package hu.gde.runnersdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/runner")
public class RunnerRestController {

    @Autowired
    private LapTimeRepository lapTimeRepository;
    private RunnerRepository runnerRepository;
    private SponsorRepository sponsorRepository;
    @Autowired
    public RunnerRestController(RunnerRepository runnerRepository, LapTimeRepository lapTimeRepository,SponsorRepository sponsorRepository) {
        this.runnerRepository = runnerRepository;
        this.lapTimeRepository = lapTimeRepository;
        this.sponsorRepository = sponsorRepository;
    }

    @GetMapping("/{id}")
    public RunnerEntity getRunner(@PathVariable Long id) {
        return runnerRepository.findById(id).orElse(null);
    }
    @GetMapping("{id}/{sponsorId}/setrunnerssponsor")
    public void setRunnersSponsor(@PathVariable Long id,@PathVariable Long sponsorId){
        RunnerEntity runner = runnerRepository.findById(id).orElse(null);
        List<SponsorEntity> sponsors = sponsorRepository.findAll();
        if(runner!=null &&  sponsors!=null){
            for(SponsorEntity s : sponsors){
                if(s.getSponsorId()==sponsorId){
                    runner.setSponsorEntity(s);
                }
            }
        }
    }
    @GetMapping("/{id}/averagelaptime")
    public double getAverageLaptime(@PathVariable Long id) {
        RunnerEntity runner = runnerRepository.findById(id).orElse(null);
        if (runner != null) {
            List<LapTimeEntity> laptimes = runner.getLaptimes();
            int totalTime = 0;
            for (LapTimeEntity laptime : laptimes) {
                totalTime += laptime.getTimeSeconds();
            }
            double averageLaptime = (double) totalTime / laptimes.size();
            return averageLaptime;
        } else {
            return -1.0;
        }
    }
    @GetMapping("/tallestrunnersname")
    public String getTallestRunnersName(){
        List<RunnerEntity> runners = runnerRepository.findAll();
        if(runners != null){
            int lastTallestHeight = 0;
            RunnerEntity tallestRunner = null;
            for(RunnerEntity r : runners){
                int temporaryHeight = r.getRunnerHeight();
                if(temporaryHeight>lastTallestHeight) {
                    lastTallestHeight = temporaryHeight;
                    tallestRunner = r;
                }
            }
            return tallestRunner.getRunnerName();
        } else {
            return null;
        }
    }

    @GetMapping("")
    public List<RunnerEntity> getAllRunners() {
        return runnerRepository.findAll();
    }

    @PostMapping("/{id}/addlaptime")
    public ResponseEntity addLaptime(@PathVariable Long id, @RequestBody LapTimeRequest lapTimeRequest) {
        RunnerEntity runner = runnerRepository.findById(id).orElse(null);
        if (runner != null) {
            LapTimeEntity lapTime = new LapTimeEntity();
            lapTime.setTimeSeconds(lapTimeRequest.getLapTimeSeconds());
            lapTime.setLapNumber(runner.getLaptimes().size() + 1);
            lapTime.setRunner(runner);
            lapTimeRepository.save(lapTime);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Runner with ID " + id + " not found");
        }
    }
    public static class LapTimeRequest {
        private int lapTimeSeconds;

        public int getLapTimeSeconds() {
            return lapTimeSeconds;
        }

        public void setLapTimeSeconds(int lapTimeSeconds) {
            this.lapTimeSeconds = lapTimeSeconds;
        }
    }
    public static class SponsorRequest{
        public int sponsorId;
        public int getSponsorId() {
            return sponsorId;
        }

        public void setSponsorId(int sponsorId) {
            this.sponsorId = sponsorId;
        }

    }
}
