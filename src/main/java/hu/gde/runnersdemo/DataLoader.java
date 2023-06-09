package hu.gde.runnersdemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class DataLoader implements CommandLineRunner {
    private final SponsorRepository sponsorRepository;

    private final RunnerRepository runnerRepository;

    @Autowired
    public DataLoader(RunnerRepository runnerRepository,SponsorRepository sponsorRepository) {
        this.runnerRepository = runnerRepository;
        this.sponsorRepository = sponsorRepository;
    }

    @Override
    public void run(String... args) {
        SponsorEntity sponsorEntity1 = new SponsorEntity();
        sponsorEntity1.setSponsorName("Nájki");
        sponsorRepository.save(sponsorEntity1);
        SponsorEntity sponsorEntity2 = new SponsorEntity();
        sponsorEntity2.setSponsorName("RIBUKK");
        sponsorRepository.save(sponsorEntity2);
        SponsorEntity sponsorEntity3 = new SponsorEntity();
        sponsorEntity3.setSponsorName("SaDiDa");
        sponsorRepository.save(sponsorEntity3);


        RunnerEntity runnerEntity = new RunnerEntity();
        runnerEntity.setRunnerName("Tomi");
        runnerEntity.setAveragePace(310);
        runnerEntity.setRunnerHeight(174);

        LapTimeEntity laptime1 = new LapTimeEntity();
        laptime1.setLapNumber(1);
        laptime1.setTimeSeconds(120);
        laptime1.setRunner(runnerEntity);

        LapTimeEntity laptime2 = new LapTimeEntity();
        laptime2.setLapNumber(2);
        laptime2.setTimeSeconds(110);
        laptime2.setRunner(runnerEntity);

        runnerEntity.getLaptimes().add(laptime1);
        runnerEntity.getLaptimes().add(laptime2);
        runnerEntity.setSponsorEntity(sponsorEntity1);
        runnerRepository.save(runnerEntity);

        RunnerEntity runnerEntity2 = new RunnerEntity();
        runnerEntity2.setRunnerName("Zsuzsi");
        runnerEntity2.setAveragePace(290);
        runnerEntity2.setRunnerHeight(150);

        LapTimeEntity laptime3 = new LapTimeEntity();
        laptime3.setLapNumber(1);
        laptime3.setTimeSeconds(95);
        laptime3.setRunner(runnerEntity2);

        LapTimeEntity laptime4 = new LapTimeEntity();
        laptime4.setLapNumber(2);
        laptime4.setTimeSeconds(100);
        laptime4.setRunner(runnerEntity2);

        runnerEntity2.getLaptimes().add(laptime3);
        runnerEntity2.getLaptimes().add(laptime4);

        runnerEntity2.setSponsorEntity(sponsorEntity2);
        runnerRepository.save(runnerEntity2);

        RunnerEntity runnerEntity3 = new RunnerEntity();
        runnerEntity3.setRunnerName("Peti");
        runnerEntity3.setAveragePace(400);
        runnerEntity3.setRunnerHeight(181);

        LapTimeEntity laptime5 = new LapTimeEntity();
        laptime5.setLapNumber(1);
        laptime5.setTimeSeconds(60);
        laptime5.setRunner(runnerEntity3);

        LapTimeEntity laptime6 = new LapTimeEntity();
        laptime6.setLapNumber(2);
        laptime6.setTimeSeconds(67);
        laptime6.setRunner(runnerEntity3);

        runnerEntity3.getLaptimes().add(laptime5);
        runnerEntity3.getLaptimes().add(laptime6);

        runnerEntity3.setSponsorEntity(sponsorEntity1);
        runnerRepository.save(runnerEntity3);
    }
}

