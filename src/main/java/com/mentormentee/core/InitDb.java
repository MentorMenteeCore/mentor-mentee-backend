package com.mentormentee.core;

import com.mentormentee.core.domain.*;
import com.mentormentee.core.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.mentormentee.core.domain.Role.ROLE_MENTEE;
import static com.mentormentee.core.domain.Role.ROLE_MENTOR;
import static com.mentormentee.core.domain.WaysOfCommunication.FACETOFACE;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;
    private static String defaultProfileImage;

    @Value("${spring.defaultProfileImage}")
    public void setDefaultProfileImage(String defaultProfileImage) {
        InitDb.defaultProfileImage = defaultProfileImage;
    }


    @PostConstruct
    public void init() {
        initService.dbInit1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final EntityManager em;
        private final PasswordEncoder passwordEncoder;

        public void dbInit1() {

            // Humanities College
            College humanitiesCollege = new College();
            humanitiesCollege.createCollege(CollegeName.HUMANITIES);
            em.persist(humanitiesCollege);

            Department koreanDepartment = new Department();
            koreanDepartment.createDepartment("국어국문학과", "www.exampleImage1.com", humanitiesCollege);
            em.persist(koreanDepartment);

            Department chineseDepartment = new Department();
            chineseDepartment.createDepartment("중어중문학과", "www.exampleImage2.com", humanitiesCollege);
            em.persist(chineseDepartment);

            Department englishDepartment = new Department();
            englishDepartment.createDepartment("영어영문학과", "www.exampleImage3.com", humanitiesCollege);
            em.persist(englishDepartment);

            Department germanDepartment = new Department();
            germanDepartment.createDepartment("독일언어문화학과", "www.exampleImage4.com", humanitiesCollege);
            em.persist(germanDepartment);

            Department frenchDepartment = new Department();
            frenchDepartment.createDepartment("프랑스언어문화학과", "www.exampleImage5.com", humanitiesCollege);
            em.persist(frenchDepartment);

            Department russianDepartment = new Department();
            russianDepartment.createDepartment("러시아언어문화학과", "www.exampleImage6.com", humanitiesCollege);
            em.persist(russianDepartment);

            Department philosophyDepartment = new Department();
            philosophyDepartment.createDepartment("철학과", "www.exampleImage7.com", humanitiesCollege);
            em.persist(philosophyDepartment);

            Department historyDepartment = new Department();
            historyDepartment.createDepartment("사학과", "www.exampleImage8.com", humanitiesCollege);
            em.persist(historyDepartment);

            Department archaeologyDepartment = new Department();
            archaeologyDepartment.createDepartment("고고미술사학과", "www.exampleImage9.com", humanitiesCollege);
            em.persist(archaeologyDepartment);

            // Social Sciences College
            College socialSciencesCollege = new College();
            socialSciencesCollege.createCollege(CollegeName.SOCIALSCIENCES);
            em.persist(socialSciencesCollege);

            Department sociologyDepartment = new Department();
            sociologyDepartment.createDepartment("사회학과", "www.exampleImage10.com", socialSciencesCollege);
            em.persist(sociologyDepartment);

            Department psychologyDepartment = new Department();
            psychologyDepartment.createDepartment("심리학과", "www.exampleImage11.com", socialSciencesCollege);
            em.persist(psychologyDepartment);

            Department administrationDepartment = new Department();
            administrationDepartment.createDepartment("행정학과", "www.exampleImage12.com", socialSciencesCollege);
            em.persist(administrationDepartment);

            Department politicalScienceDepartment = new Department();
            politicalScienceDepartment.createDepartment("정치외교학과", "www.exampleImage13.com", socialSciencesCollege);
            em.persist(politicalScienceDepartment);

            Department economicsDepartment = new Department();
            economicsDepartment.createDepartment("경제학과", "www.exampleImage14.com", socialSciencesCollege);
            em.persist(economicsDepartment);

            // Nature Science College
            College natureScienceCollege = new College();
            natureScienceCollege.createCollege(CollegeName.NATURESCIENCE);
            em.persist(natureScienceCollege);

            Department mathematicsDepartment = new Department();
            mathematicsDepartment.createDepartment("수학과", "www.exampleImage15.com", natureScienceCollege);
            em.persist(mathematicsDepartment);

            Department statisticsDepartment = new Department();
            statisticsDepartment.createDepartment("정보통계학과", "www.exampleImage16.com", natureScienceCollege);
            em.persist(statisticsDepartment);

            Department physicsDepartment = new Department();
            physicsDepartment.createDepartment("물리학과", "www.exampleImage17.com", natureScienceCollege);
            em.persist(physicsDepartment);

            Department chemistryDepartment = new Department();
            chemistryDepartment.createDepartment("화학과", "www.exampleImage18.com", natureScienceCollege);
            em.persist(chemistryDepartment);

            Department biologyDepartment = new Department();
            biologyDepartment.createDepartment("생물학과", "www.exampleImage19.com", natureScienceCollege);
            em.persist(biologyDepartment);

            Department microbiologyDepartment = new Department();
            microbiologyDepartment.createDepartment("미생물학과", "www.exampleImage20.com", natureScienceCollege);
            em.persist(microbiologyDepartment);

            Department biochemistryDepartment = new Department();
            biochemistryDepartment.createDepartment("생화학과", "www.exampleImage21.com", natureScienceCollege);
            em.persist(biochemistryDepartment);

            Department astronomyDepartment = new Department();
            astronomyDepartment.createDepartment("천문우주학과", "www.exampleImage22.com", natureScienceCollege);
            em.persist(astronomyDepartment);

            Department earthScienceDepartment = new Department();
            earthScienceDepartment.createDepartment("지구환경과학과", "www.exampleImage23.com", natureScienceCollege);
            em.persist(earthScienceDepartment);
            College businessCollege = new College();
            businessCollege.createCollege(CollegeName.BUSINESS);
            em.persist(businessCollege);

            Department businessAdministrationDepartment = new Department();
            businessAdministrationDepartment.createDepartment("경영학부", "www.exampleImage24.com", businessCollege);
            em.persist(businessAdministrationDepartment);

            Department internationalBusinessDepartment = new Department();
            internationalBusinessDepartment.createDepartment("국제경영학과", "www.exampleImage25.com", businessCollege);
            em.persist(internationalBusinessDepartment);

            Department businessInformationDepartment = new Department();
            businessInformationDepartment.createDepartment("경영정보학과", "www.exampleImage26.com", businessCollege);
            em.persist(businessInformationDepartment);

            // Computer Engineering College
            College computerEngineeringCollege = new College();
            computerEngineeringCollege.createCollege(CollegeName.COMPUTERENGINEERING);
            em.persist(computerEngineeringCollege);

            Department electricalEngineeringDepartment = new Department();
            electricalEngineeringDepartment.createDepartment("전기공학부", "www.exampleImage27.com", computerEngineeringCollege);
            em.persist(electricalEngineeringDepartment);

            Department electronicEngineeringDepartment = new Department();
            electronicEngineeringDepartment.createDepartment("전자공학부", "www.exampleImage28.com", computerEngineeringCollege);
            em.persist(electronicEngineeringDepartment);

            Department informationCommunicationDepartment = new Department();
            informationCommunicationDepartment.createDepartment("정보통신공학부", "www.exampleImage29.com", computerEngineeringCollege);
            em.persist(informationCommunicationDepartment);

            Department computerScienceDepartment = new Department();
            computerScienceDepartment.createDepartment("컴퓨터공학과", "www.exampleImage30.com", computerEngineeringCollege);
            em.persist(computerScienceDepartment);

            Department softwareEngineeringDepartment = new Department();
            softwareEngineeringDepartment.createDepartment("소프트웨어학과", "www.exampleImage31.com", computerEngineeringCollege);
            em.persist(softwareEngineeringDepartment);

            Department intelligentRoboticsDepartment = new Department();
            intelligentRoboticsDepartment.createDepartment("지능로봇공학과", "www.exampleImage32.com", computerEngineeringCollege);
            em.persist(intelligentRoboticsDepartment);

            // Agriculture College
            College agricultureCollege = new College();
            agricultureCollege.createCollege(CollegeName.AGRICULTURE);
            em.persist(agricultureCollege);

            Department plantResourcesDepartment = new Department();
            plantResourcesDepartment.createDepartment("식물자원학과", "www.exampleImage33.com", agricultureCollege);
            em.persist(plantResourcesDepartment);

            Department livestockDepartment = new Department();
            livestockDepartment.createDepartment("축산학과", "www.exampleImage34.com", agricultureCollege);
            em.persist(livestockDepartment);

            Department forestryDepartment = new Department();
            forestryDepartment.createDepartment("산림학과", "www.exampleImage35.com", agricultureCollege);
            em.persist(forestryDepartment);

            Department regionalConstructionDepartment = new Department();
            regionalConstructionDepartment.createDepartment("지역건설공학과", "www.exampleImage36.com", agricultureCollege);
            em.persist(regionalConstructionDepartment);

            Department environmentalLifeChemistryDepartment = new Department();
            environmentalLifeChemistryDepartment.createDepartment("환경생명화학과", "www.exampleImage37.com", agricultureCollege);
            em.persist(environmentalLifeChemistryDepartment);

            Department specialPlantsDepartment = new Department();
            specialPlantsDepartment.createDepartment("특용식물학과", "www.exampleImage38.com", agricultureCollege);
            em.persist(specialPlantsDepartment);

            Department horticulturalScienceDepartment = new Department();
            horticulturalScienceDepartment.createDepartment("원예과학과", "www.exampleImage39.com", agricultureCollege);
            em.persist(horticulturalScienceDepartment);

            Department biosystemsEngineeringDepartment = new Department();
            biosystemsEngineeringDepartment.createDepartment("바이오시스템공학과", "www.exampleImage40.com", agricultureCollege);
            em.persist(biosystemsEngineeringDepartment);

            Department plantMedicalDepartment = new Department();
            plantMedicalDepartment.createDepartment("식물의학과", "www.exampleImage41.com", agricultureCollege);
            em.persist(plantMedicalDepartment);

            Department foodScienceDepartment = new Department();
            foodScienceDepartment.createDepartment("식품생명공학과", "www.exampleImage42.com", agricultureCollege);
            em.persist(foodScienceDepartment);

            Department woodPaperScienceDepartment = new Department();
            woodPaperScienceDepartment.createDepartment("목재 종이과학과", "www.exampleImage43.com", agricultureCollege);
            em.persist(woodPaperScienceDepartment);

            Department agriculturalEconomicsDepartment = new Department();
            agriculturalEconomicsDepartment.createDepartment("농업경제학과", "www.exampleImage44.com", agricultureCollege);
            em.persist(agriculturalEconomicsDepartment);

            // Human Ecology College
            College humanEcologyCollege = new College();
            humanEcologyCollege.createCollege(CollegeName.HUMANECOLOGY);
            em.persist(humanEcologyCollege);

            Department foodAndNutritionDepartment = new Department();
            foodAndNutritionDepartment.createDepartment("식품영양학과", "www.exampleImage45.com", humanEcologyCollege);
            em.persist(foodAndNutritionDepartment);

            Department childWelfareDepartment = new Department();
            childWelfareDepartment.createDepartment("아동복지학과", "www.exampleImage46.com", humanEcologyCollege);
            em.persist(childWelfareDepartment);

            Department clothingAndTextilesDepartment = new Department();
            clothingAndTextilesDepartment.createDepartment("의류학과", "www.exampleImage47.com", humanEcologyCollege);
            em.persist(clothingAndTextilesDepartment);

            Department housingAndInteriorDesignDepartment = new Department();
            housingAndInteriorDesignDepartment.createDepartment("주거환경학과", "www.exampleImage48.com", humanEcologyCollege);
            em.persist(housingAndInteriorDesignDepartment);

            Department consumerScienceDepartment = new Department();
            consumerScienceDepartment.createDepartment("소비자학과", "www.exampleImage49.com", humanEcologyCollege);
            em.persist(consumerScienceDepartment);

            // Convergence College
            College convergenceCollege = new College();
            convergenceCollege.createCollege(CollegeName.CONVERGENCE);
            em.persist(convergenceCollege);

            Department fineArtsDepartment = new Department();
            fineArtsDepartment.createDepartment("조형예술학과", "www.exampleImage50.com", convergenceCollege);
            em.persist(fineArtsDepartment);

            Department designDepartment = new Department();
            designDepartment.createDepartment("디자인학과", "www.exampleImage51.com", convergenceCollege);
            em.persist(designDepartment);

            // Interdisciplinary Studies
            College interdisciplinaryCollege = new College();
            interdisciplinaryCollege.createCollege(CollegeName.INTERDISCIPLINARYSTUDIES);
            em.persist(interdisciplinaryCollege);

            Department interdisciplinaryStudiesDepartment = new Department();
            interdisciplinaryStudiesDepartment.createDepartment("자율전공학부", "www.exampleImage52.com", interdisciplinaryCollege);
            em.persist(interdisciplinaryStudiesDepartment);

            // Engineering College
            College engineeringCollege = new College();
            engineeringCollege.createCollege(CollegeName.ENGINEERING);
            em.persist(engineeringCollege);

            Department civilEngineeringDepartment = new Department();
            civilEngineeringDepartment.createDepartment("토목공학부", "www.exampleImage53.com", engineeringCollege);
            em.persist(civilEngineeringDepartment);

            Department mechanicalEngineeringDepartment = new Department();
            mechanicalEngineeringDepartment.createDepartment("기계공학부", "www.exampleImage54.com", engineeringCollege);
            em.persist(mechanicalEngineeringDepartment);

            Department chemicalEngineeringDepartment = new Department();
            chemicalEngineeringDepartment.createDepartment("화학공학과", "www.exampleImage55.com", engineeringCollege);
            em.persist(chemicalEngineeringDepartment);

            Department materialsScienceDepartment = new Department();
            materialsScienceDepartment.createDepartment("신소재공학과", "www.exampleImage56.com", engineeringCollege);
            em.persist(materialsScienceDepartment);

            Department architecturalEngineeringDepartment = new Department();
            architecturalEngineeringDepartment.createDepartment("건축공학과", "www.exampleImage57.com", engineeringCollege);
            em.persist(architecturalEngineeringDepartment);

            Department safetyEngineeringDepartment = new Department();
            safetyEngineeringDepartment.createDepartment("안전공학과", "www.exampleImage58.com", engineeringCollege);
            em.persist(safetyEngineeringDepartment);

            Department environmentalEngineeringDepartment = new Department();
            environmentalEngineeringDepartment.createDepartment("환경공학과", "www.exampleImage59.com", engineeringCollege);
            em.persist(environmentalEngineeringDepartment);

            Department industrialChemistryDepartment = new Department();
            industrialChemistryDepartment.createDepartment("공업화학과", "www.exampleImage60.com", engineeringCollege);
            em.persist(industrialChemistryDepartment);

            Department urbanEngineeringDepartment = new Department();
            urbanEngineeringDepartment.createDepartment("도시공학과", "www.exampleImage61.com", engineeringCollege);
            em.persist(urbanEngineeringDepartment);

            Department architectureDepartment = new Department();
            architectureDepartment.createDepartment("건축학과", "www.exampleImage62.com", engineeringCollege);
            em.persist(architectureDepartment);

            // Education College
            College educationCollege = new College();
            educationCollege.createCollege(CollegeName.EDUCATION);
            em.persist(educationCollege);

            Department educationDepartment = new Department();
            educationDepartment.createDepartment("교육학과", "www.exampleImage63.com", educationCollege);
            em.persist(educationDepartment);

            Department koreanEducationDepartment = new Department();
            koreanEducationDepartment.createDepartment("국어교육과", "www.exampleImage64.com", educationCollege);
            em.persist(koreanEducationDepartment);

            Department englishEducationDepartment = new Department();
            englishEducationDepartment.createDepartment("영어교육과", "www.exampleImage65.com", educationCollege);
            em.persist(englishEducationDepartment);

            Department historyEducationDepartment = new Department();
            historyEducationDepartment.createDepartment("역사교육과", "www.exampleImage66.com", educationCollege);
            em.persist(historyEducationDepartment);

            Department geographyEducationDepartment = new Department();
            geographyEducationDepartment.createDepartment("지리교육과", "www.exampleImage67.com", educationCollege);
            em.persist(geographyEducationDepartment);

            Department socialEducationDepartment = new Department();
            socialEducationDepartment.createDepartment("사회교육과", "www.exampleImage68.com", educationCollege);
            em.persist(socialEducationDepartment);

            Department ethicsEducationDepartment = new Department();
            ethicsEducationDepartment.createDepartment("윤리교육과", "www.exampleImage69.com", educationCollege);
            em.persist(ethicsEducationDepartment);

            Department physicsEducationDepartment = new Department();
            physicsEducationDepartment.createDepartment("물리교육과", "www.exampleImage70.com", educationCollege);
            em.persist(physicsEducationDepartment);

            Department chemistryEducationDepartment = new Department();
            chemistryEducationDepartment.createDepartment("화학교육과", "www.exampleImage71.com", educationCollege);
            em.persist(chemistryEducationDepartment);

            Department biologyEducationDepartment = new Department();
            biologyEducationDepartment.createDepartment("생물교육과", "www.exampleImage72.com", educationCollege);
            em.persist(biologyEducationDepartment);

            Department earthScienceEducationDepartment = new Department();
            earthScienceEducationDepartment.createDepartment("지구과학교육과", "www.exampleImage73.com", educationCollege);
            em.persist(earthScienceEducationDepartment);

            Department mathEducationDepartment = new Department();
            mathEducationDepartment.createDepartment("수학교육과", "www.exampleImage74.com", educationCollege);
            em.persist(mathEducationDepartment);

            Department physicalEducationDepartment = new Department();
            physicalEducationDepartment.createDepartment("체육교육과", "www.exampleImage75.com", educationCollege);
            em.persist(physicalEducationDepartment);

            // Veterinary Medicine College
            College veterinaryMedicineCollege = new College();
            veterinaryMedicineCollege.createCollege(CollegeName.VETERINARYMEDICINE);
            em.persist(veterinaryMedicineCollege);

            Department preVeterinaryMedicineDepartment = new Department();
            preVeterinaryMedicineDepartment.createDepartment("수의예과", "www.exampleImage76.com", veterinaryMedicineCollege);
            em.persist(preVeterinaryMedicineDepartment);

            Department veterinaryMedicineDepartment = new Department();
            veterinaryMedicineDepartment.createDepartment("수의학과", "www.exampleImage77.com", veterinaryMedicineCollege);
            em.persist(veterinaryMedicineDepartment);

            // Pharmacy College
            College pharmacyCollege = new College();
            pharmacyCollege.createCollege(CollegeName.PHARMACY);
            em.persist(pharmacyCollege);

            Department pharmacyDepartment = new Department();
            pharmacyDepartment.createDepartment("약학과", "www.exampleImage78.com", pharmacyCollege);
            em.persist(pharmacyDepartment);

            Department pharmaceuticalSciencesDepartment = new Department();
            pharmaceuticalSciencesDepartment.createDepartment("제약학과", "www.exampleImage79.com", pharmacyCollege);
            em.persist(pharmaceuticalSciencesDepartment);

            // Medicine College
            College medicineCollege = new College();
            medicineCollege.createCollege(CollegeName.MEDICINE);
            em.persist(medicineCollege);

            Department preMedicalDepartment = new Department();
            preMedicalDepartment.createDepartment("의예과", "www.exampleImage80.com", medicineCollege);
            em.persist(preMedicalDepartment);

            Department medicalDepartment = new Department();
            medicalDepartment.createDepartment("의학과", "www.exampleImage81.com", medicineCollege);
            em.persist(medicalDepartment);

            Department nursingDepartment = new Department();
            nursingDepartment.createDepartment("간호학과", "www.exampleImage82.com", medicineCollege);
            em.persist(nursingDepartment);

            // Biohealth System College
            College biohealthSystemCollege = new College();
            biohealthSystemCollege.createCollege(CollegeName.BIOHEALTHSYSTEM);
            em.persist(biohealthSystemCollege);

            Department bioHealthDepartment = new Department();
            bioHealthDepartment.createDepartment("바이오헬스학부", "www.exampleImage83.com", biohealthSystemCollege);
            em.persist(bioHealthDepartment);

            Department bioHealthcareMajorDepartment = new Department();
            bioHealthcareMajorDepartment.createDepartment("바이오헬스케어전공", "www.exampleImage84.com", biohealthSystemCollege);
            em.persist(bioHealthcareMajorDepartment);

            Department veterinaryLifeHealthMajorDepartment = new Department();
            veterinaryLifeHealthMajorDepartment.createDepartment("수의생명보건전공", "www.exampleImage85.com", biohealthSystemCollege);
            em.persist(veterinaryLifeHealthMajorDepartment);

            Department bioHealthIndustryMajorDepartment = new Department();
            bioHealthIndustryMajorDepartment.createDepartment("바이오헬스산업전공", "www.exampleImage86.com", biohealthSystemCollege);
            em.persist(bioHealthIndustryMajorDepartment);

            User user1 = new User();
            user1.createUser(
                    "choegiyeon", "choegi", ROLE_MENTEE, "choegi@example.com",
                    "password123",
                    FACETOFACE, 2, "www.exampleProfilePicture1.com",
                    computerScienceDepartment, "sampleRefreshToken1","최기연입니다. 충북대 학생 입니다. 안녕하세요! "
            );
            new AvailableTime(user1, DayOfWeek.FRIDAY, LocalTime.of(9, 0), LocalTime.of(14, 0));
            new AvailableTime(user1, DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(14, 0));
            user1.hashPassword(passwordEncoder);

            
            em.persist(user1);

            User user2 = new User();
            user2.createUser(
                    "박상현", "나는야박상", ROLE_MENTOR, "cs1@example.com",
                    "password1234",
                    FACETOFACE, 2, "www.exampleProfilePicture2.com",
                    informationCommunicationDepartment, "sampleRefreshToken2","선배 탕후루도 같이"
            );
            user2.hashPassword(passwordEncoder);

            em.persist(user2);

            User user3 = new User();
            user3.createUser(
                    "최기연", "어디로가야하오", ROLE_MENTEE, "bdcgy22@daum.net",
                    "password12345",
                    FACETOFACE, 2, "www.exampleProfilePicture3.com",
                    informationCommunicationDepartment, "sampleRefreshToken3","저는 충북대 컴공을 전공중인 멘토입니다. 어서 저에게 연락을 주세요!"
            );
            user3.hashPassword(passwordEncoder);

            Review review = new Review();
            review.createDate(2024,2,3);
            review.createReview(user2, 3, "좋은 사람 근데 가끔 냄새나요");
            Review review1 = new Review();
            review1.createDate(2024,3,2);
            review1.createReview(user2, 5, "친절헤여");

            AvailableTime availableTime1 = new AvailableTime(user3, DayOfWeek.FRIDAY, LocalTime.of(9, 0), LocalTime.of(14, 0));
            AvailableTime availableTime2 = new AvailableTime(user3, DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(14, 0));
            AvailableTime availableTime3 = new AvailableTime(user2, DayOfWeek.FRIDAY, LocalTime.of(9, 0), LocalTime.of(14, 0));
            AvailableTime availableTime4 = new AvailableTime(user2, DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(14, 0));

            em.persist(user3);
            em.persist(availableTime1);
            em.persist(availableTime2);
            em.persist(availableTime3);
            em.persist(availableTime4);
            em.persist(review1);
            em.persist(review);
//
//            PreferredTeachingMethod preferredTeachingMethod1 = new PreferredTeachingMethod();
//            preferredTeachingMethod1.createTeachingMethod("자기주도_학습_야자_싫어");
//            em.persist(preferredTeachingMethod1);
//
//            PreferredTeachingMethod preferredTeachingMethod2 = new PreferredTeachingMethod();
//            preferredTeachingMethod2.createTeachingMethod("교수님과_함께하는_수업");
//            em.persist(preferredTeachingMethod2);
//
//            PreferredTeachingMethod preferredTeachingMethod3 = new PreferredTeachingMethod();
//            preferredTeachingMethod3.createTeachingMethod("멘토와 줌으로");
//            em.persist(preferredTeachingMethod3);
//
//            PreferredTeachingMethod preferredTeachingMethod4 = new PreferredTeachingMethod();
//            preferredTeachingMethod4.createTeachingMethod("커뮤니케이션 중요");
//            em.persist(preferredTeachingMethod4);
//
//            PreferredTeachingMethod preferredTeachingMethod5 = new PreferredTeachingMethod();
//            preferredTeachingMethod5.createTeachingMethod("카톡 중요");
//            em.persist(preferredTeachingMethod5);

            User user4 = new User();
            user4.createUser(
                    "최만평", "네로", ROLE_MENTOR, "cs3@example.com",
                    "password111",
                    FACETOFACE, 4, defaultProfileImage,
                    informationCommunicationDepartment, "sampleRefreshToken4","안녕"
            );
            user4.hashPassword(passwordEncoder);
            em.persist(user4);

            User user5 = new User();
            user5.createUser(
                    "최억평", "하잇", ROLE_MENTOR, "cs4@example.com",
                    "password11",
                    FACETOFACE, 2, defaultProfileImage,
                    informationCommunicationDepartment, "sampleRefreshToken4","안녕"
            );
            user5.hashPassword(passwordEncoder);
            em.persist(user5);

            User user6 = new User();
            user6.createUser(
                    "최조평", "동동이", ROLE_MENTOR, "cs5@example.com",
                    "password111111",
                    FACETOFACE, 3, defaultProfileImage,
                    informationCommunicationDepartment, "sampleRefreshToken4","안녕"
            );
            user6.hashPassword(passwordEncoder);
            em.persist(user6);

            User user7 = new User();
            user7.createUser(
                    "타학과생", "난달라달라", ROLE_MENTOR, "cs6@example.com",
                    "password1212",
                    FACETOFACE, 3, defaultProfileImage,
                    informationCommunicationDepartment, "sampleRefreshToken5","안녕"
            );
            user7.hashPassword(passwordEncoder);
            em.persist(user7);

            User user8 = new User();
            user7.createUser(
                    "최기연연", "최기연연연", ROLE_MENTOR, "cs6@example.com",
                    "password1212332",
                    FACETOFACE, 4, defaultProfileImage,
                    informationCommunicationDepartment, "sampleRefreshToken5","언뇽허새용"
            );
            user8.hashPassword(passwordEncoder);
            em.persist(user8);

            ChatRoom chatRoom0 = new ChatRoom(LocalDateTime.now(), ChatRoom.getRoomId(user2.getId().toString(), user5.getId().toString()), user2.getId(), user5.getId());
            em.persist(chatRoom0);

            ChatRoom chatRoom1 = new ChatRoom(LocalDateTime.now(), ChatRoom.getRoomId(user2.getId().toString(), user1.getId().toString()), user2.getId(), user1.getId());
            em.persist(chatRoom1);

            ChatRoom chatRoom2 = new ChatRoom(LocalDateTime.now(), ChatRoom.getRoomId(user2.getId().toString(), user3.getId().toString()), user2.getId(), user3.getId());
            em.persist(chatRoom2);

            ChatRoom chatRoom3 = new ChatRoom(LocalDateTime.now(), ChatRoom.getRoomId(user2.getId().toString(), user4.getId().toString()), user2.getId(), user4.getId());
            em.persist(chatRoom3);

            // 메시지 생성 (채팅방마다 여러 메시지 추가)
            // 채팅방 1 (박상현 <-> 최기연)
            Message message1 = new Message();
            message1.setChatRoom(chatRoom1);
            message1.setUser(user2); // 박상현이 보낸 메시지
            message1.setContent("안녕하세요, 최기연 님!");
            message1.setTime(LocalDateTime.now().minusMinutes(15));
            message1.setReadOrNot(true);
            em.persist(message1);

            Message message2 = new Message();
            message2.setChatRoom(chatRoom1);
            message2.setUser(user1); // 최기연이 보낸 메시지
            message2.setContent("안녕하세요, 박상현 선배님!");
            message2.setTime(LocalDateTime.now().minusMinutes(10));
            message2.setReadOrNot(false);
            em.persist(message2);

            Message message3 = new Message();
            message3.setChatRoom(chatRoom1);
            message3.setUser(user2);
            message3.setContent("오늘 시간 되시나요?");
            message3.setTime(LocalDateTime.now().minusMinutes(5));
            message3.setReadOrNot(false);
            em.persist(message3);

            // 채팅방 2 (박상현 <-> 최기연)
            Message message4 = new Message();
            message4.setChatRoom(chatRoom2);
            message4.setUser(user3); // 어디로가야하오 (최기연) 이 보낸 메시지
            message4.setContent("멘토링 신청합니다!");
            message4.setTime(LocalDateTime.now().minusHours(1));
            message4.setReadOrNot(false);
            em.persist(message4);

            Message message5 = new Message();
            message5.setChatRoom(chatRoom2);
            message5.setUser(user2);
            message5.setContent("네, 가능합니다.");
            message5.setTime(LocalDateTime.now().minusMinutes(30));
            message5.setReadOrNot(false);
            em.persist(message5);

            // 채팅방 3 (박상현 <-> 최만평)
            Message message6 = new Message();
            message6.setChatRoom(chatRoom3);
            message6.setUser(user4); // 네로 (최만평) 이 보낸 메시지
            message6.setContent("안녕하세요?");
            message6.setTime(LocalDateTime.now().minusDays(1));
            message6.setReadOrNot(false);
            em.persist(message6);

            Message message7 = new Message();
            message7.setChatRoom(chatRoom3);
            message7.setUser(user2);
            message7.setContent("안녕하세요.");
            message7.setTime(LocalDateTime.now().minusHours(23));
            message7.setReadOrNot(false);
            em.persist(message7);

            // 추가 메시지 생성 (테스트 데이터 풍부하게)
            // 채팅방 1에 추가 메시지
            Message message8 = new Message();
            message8.setChatRoom(chatRoom1);
            message8.setUser(user1);
            message8.setContent("네, 오늘 오후 3시에 뵐까요?");
            message8.setTime(LocalDateTime.now().minusMinutes(2));
            message8.setReadOrNot(false);
            em.persist(message8);

            Message message9 = new Message();
            message9.setChatRoom(chatRoom1);
            message9.setUser(user2);
            message9.setContent("좋습니다. 장소는 학생회관 1층에서 만나요.");
            message9.setTime(LocalDateTime.now().minusMinutes(1));
            message9.setReadOrNot(false);
            em.persist(message9);



            UserPreferredTeachingMethod userPreferredTeachingMethod1 = new UserPreferredTeachingMethod();
            userPreferredTeachingMethod1.createUserMethod(user2,"자기주도_학습_야자_싫어");
            em.persist(userPreferredTeachingMethod1);

            UserPreferredTeachingMethod userPreferredTeachingMethod2 = new UserPreferredTeachingMethod();
            userPreferredTeachingMethod2.createUserMethod(user2,"교수님과_함께하는_수업");
            em.persist(userPreferredTeachingMethod2);

            UserPreferredTeachingMethod userPreferredTeachingMethod3 = new UserPreferredTeachingMethod();
            userPreferredTeachingMethod3.createUserMethod(user3,"멘토와 줌으로");
            em.persist(userPreferredTeachingMethod3);

            UserPreferredTeachingMethod userPreferredTeachingMethod4 = new UserPreferredTeachingMethod();
            userPreferredTeachingMethod4.createUserMethod(user3,"커뮤니케이션 중요");
            em.persist(userPreferredTeachingMethod4);

            UserPreferredTeachingMethod userPreferredTeachingMethod5 = new UserPreferredTeachingMethod();
            userPreferredTeachingMethod5.createUserMethod(user3,"카톡 중요");
            em.persist(userPreferredTeachingMethod5);


            //강의
            Course course1 = new Course();
            course1.createCourse("미래설계탐색", 1, CourseYear.FRESHMAN, informationCommunicationDepartment);
            em.persist(course1);

            Course course2 = new Course();
            course2.createCourse("미래설계준비", 0, CourseYear.FRESHMAN, informationCommunicationDepartment);
            em.persist(course2);

            Course course3 = new Course();
            course3.createCourse("오픈소스소프트웨어 이해와 실습", 1, CourseYear.FRESHMAN, informationCommunicationDepartment);
            em.persist(course3);

            Course course4 = new Course();
            course4.createCourse("정보통신개론", 3, CourseYear.SOPHOMORE, informationCommunicationDepartment);
            em.persist(course4);

            Course course5 = new Course();
            course5.createCourse("전자기학", 3, CourseYear.SOPHOMORE, informationCommunicationDepartment);
            em.persist(course5);

            Course course6 = new Course();
            course6.createCourse("회로이론1", 3, CourseYear.SOPHOMORE, informationCommunicationDepartment);
            em.persist(course6);

            Course course7 = new Course();
            course7.createCourse("공학수학1", 3, CourseYear.SOPHOMORE, informationCommunicationDepartment);
            em.persist(course7);

            Course course8 = new Course();
            course8.createCourse("회로실험1", 2, CourseYear.SOPHOMORE, informationCommunicationDepartment);
            em.persist(course8);

            Course course9 = new Course();
            course9.createCourse("미래설계구현", 0, CourseYear.SOPHOMORE, informationCommunicationDepartment);
            em.persist(course9);

            Course course10 = new Course();
            course10.createCourse("디지털공학", 3, CourseYear.SOPHOMORE, informationCommunicationDepartment);
            em.persist(course10);

            Course course11 = new Course();
            course11.createCourse("고급컴퓨터프로그래밍", 3, CourseYear.SOPHOMORE, informationCommunicationDepartment);
            em.persist(course11);

            Course course12 = new Course();
            course12.createCourse("오픈소스 기초프로젝트", 1, CourseYear.SOPHOMORE, informationCommunicationDepartment);
            em.persist(course12);

            Course course13 = new Course();
            course13.createCourse("임베디드소프트웨어실습", 3, CourseYear.SOPHOMORE, informationCommunicationDepartment);
            em.persist(course13);

            Course course14 = new Course();
            course14.createCourse("컴퓨터네트워크", 3, CourseYear.SOPHOMORE, informationCommunicationDepartment);
            em.persist(course14);

            Course course15 = new Course();
            course15.createCourse("회로실험2", 2, CourseYear.SOPHOMORE, informationCommunicationDepartment);
            em.persist(course15);

            Course course16 = new Course();
            course16.createCourse("확률및통계", 3, CourseYear.SOPHOMORE, informationCommunicationDepartment);
            em.persist(course16);

            Course course17 = new Course();
            course17.createCourse("회로이론2", 3, CourseYear.SOPHOMORE, informationCommunicationDepartment);
            em.persist(course17);

            Course course18 = new Course();
            course18.createCourse("공학수학2", 3, CourseYear.SOPHOMORE, informationCommunicationDepartment);
            em.persist(course18);

            Course course19 = new Course();
            course19.createCourse("객체지향 프로그래밍(C++)", 3, CourseYear.SOPHOMORE, informationCommunicationDepartment);
            em.persist(course19);

            Course course20 = new Course();
            course20.createCourse("오픈소스 개발프로젝트", 1, CourseYear.SOPHOMORE, informationCommunicationDepartment);
            em.persist(course20);

            Course course21 = new Course();
            course21.createCourse("소프트웨어 실전영어", 2, CourseYear.SOPHOMORE, informationCommunicationDepartment);
            em.persist(course21);

            Course course22 = new Course();
            course22.createCourse("창업탐색", 0, CourseYear.SOPHOMORE, informationCommunicationDepartment);
            em.persist(course22);

            Course course23 = new Course();
            course23.createCourse("전자회로1", 3, CourseYear.JUNIOR, informationCommunicationDepartment);
            em.persist(course23);

            Course course24 = new Course();
            course24.createCourse("통신공학", 3, CourseYear.JUNIOR, informationCommunicationDepartment);
            em.persist(course24);

            Course course25 = new Course();
            course25.createCourse("자료구조", 3, CourseYear.JUNIOR, informationCommunicationDepartment);
            em.persist(course25);

            Course course26 = new Course();
            course26.createCourse("운영체제", 3, CourseYear.JUNIOR, informationCommunicationDepartment);
            em.persist(course26);

            Course course27 = new Course();
            course27.createCourse("고주파시스템공학", 3, CourseYear.JUNIOR, informationCommunicationDepartment);
            em.persist(course27);

            Course course28 = new Course();
            course28.createCourse("신호및시스템", 3, CourseYear.JUNIOR, informationCommunicationDepartment);
            em.persist(course28);

            Course course29 = new Course();
            course29.createCourse("자바프로그래밍", 3, CourseYear.JUNIOR, informationCommunicationDepartment);
            em.persist(course29);

            Course course30 = new Course();
            course30.createCourse("데이터통신설계", 3, CourseYear.JUNIOR, informationCommunicationDepartment);
            em.persist(course30);

            Course course31 = new Course();
            course31.createCourse("오픈소스 전문프로젝트", 1, CourseYear.JUNIOR, informationCommunicationDepartment);
            em.persist(course31);

            Course course32 = new Course();
            course32.createCourse("창업기획", 0, CourseYear.JUNIOR, informationCommunicationDepartment);
            em.persist(course32);

            Course course33 = new Course();
            course33.createCourse("전자통신실험", 2, CourseYear.JUNIOR, informationCommunicationDepartment);
            em.persist(course33);

            Course course34 = new Course();
            course34.createCourse("산학프로젝트", 1, CourseYear.JUNIOR, informationCommunicationDepartment);
            em.persist(course34);

            Course course35 = new Course();
            course35.createCourse("전자회로2", 3, CourseYear.JUNIOR, informationCommunicationDepartment);
            em.persist(course35);

            Course course36 = new Course();
            course36.createCourse("디지털통신", 3, CourseYear.JUNIOR, informationCommunicationDepartment);
            em.persist(course36);

            Course course37 = new Course();
            course37.createCourse("빅데이터시스템설계", 3, CourseYear.JUNIOR, informationCommunicationDepartment);
            em.persist(course37);

            Course course38 = new Course();
            course38.createCourse("인터넷통신설계", 3, CourseYear.JUNIOR, informationCommunicationDepartment);
            em.persist(course38);

            Course course39 = new Course();
            course39.createCourse("마이크로프로세서", 3, CourseYear.JUNIOR, informationCommunicationDepartment);
            em.persist(course39);

            Course course40 = new Course();
            course40.createCourse("모바일프로그래밍및실습", 3, CourseYear.JUNIOR, informationCommunicationDepartment);
            em.persist(course40);

            Course course41 = new Course();
            course41.createCourse("디지털신호처리설계", 3, CourseYear.JUNIOR, informationCommunicationDepartment);
            em.persist(course41);

            Course course42 = new Course();
            course42.createCourse("지능형영상처리", 3, CourseYear.JUNIOR, informationCommunicationDepartment);
            em.persist(course42);

            Course course43 = new Course();
            course43.createCourse("안테나설계", 0, CourseYear.JUNIOR, informationCommunicationDepartment);
            em.persist(course43);

            Course course44 = new Course();
            course44.createCourse("창업설계", 0, CourseYear.JUNIOR, informationCommunicationDepartment);
            em.persist(course44);

            Course course45 = new Course();
            course45.createCourse("캡스톤디자인", 1, CourseYear.SENIOR, informationCommunicationDepartment);
            em.persist(course45);

            Course course46 = new Course();
            course46.createCourse("임베디드IoT응용실험", 2, CourseYear.SENIOR, informationCommunicationDepartment);
            em.persist(course46);

            Course course47 = new Course();
            course47.createCourse("광통신", 3, CourseYear.SENIOR, informationCommunicationDepartment);
            em.persist(course47);

            Course course48 = new Course();
            course48.createCourse("이동통신공학", 3, CourseYear.SENIOR, informationCommunicationDepartment);
            em.persist(course48);

            Course course49 = new Course();
            course49.createCourse("딥러닝이론및실습", 3, CourseYear.SENIOR, informationCommunicationDepartment);
            em.persist(course49);

            Course course50 = new Course();
            course50.createCourse("정보및부호이론", 3, CourseYear.SENIOR, informationCommunicationDepartment);
            em.persist(course50);

            Course course51 = new Course();
            course51.createCourse("지능형네트워크", 3, CourseYear.SENIOR, informationCommunicationDepartment);
            em.persist(course51);

            Course course52 = new Course();
            course52.createCourse("공업교육론", 3, CourseYear.SENIOR, informationCommunicationDepartment);
            em.persist(course52);

            Course course53 = new Course();
            course53.createCourse("지능형시스템", 3, CourseYear.SENIOR, informationCommunicationDepartment);
            em.persist(course53);

            Course course54 = new Course();
            course54.createCourse("무선통신망공학", 3, CourseYear.SENIOR, informationCommunicationDepartment);
            em.persist(course54);

            Course course55 = new Course();
            course55.createCourse("VLSI설계및실습", 3, CourseYear.SENIOR, informationCommunicationDepartment);
            em.persist(course55);

            Course course56 = new Course();
            course56.createCourse("창업산학초청세미나Ⅱ", 1, CourseYear.SENIOR, informationCommunicationDepartment);
            em.persist(course56);

            Course course57 = new Course();
            course57.createCourse("창업파일럿프로젝트", 1, CourseYear.SENIOR, informationCommunicationDepartment);
            em.persist(course57);

            Course course58 = new Course();
            course58.createCourse("공업논리및논술", 1, CourseYear.SENIOR, informationCommunicationDepartment);
            em.persist(course58);


            Course course59 = new Course();
            course59.createCourse("국문학개론", 1, CourseYear.FRESHMAN, koreanDepartment);
            em.persist(course59);

            Course course60 = new Course();
            course60.createCourse("국어학개론", 3, CourseYear.FRESHMAN, koreanDepartment);
            em.persist(course60);

            Course course61 = new Course();
            course61.createCourse("국문학사", 3, CourseYear.FRESHMAN, koreanDepartment);
            em.persist(course61);

            Course course62 = new Course();
            course62.createCourse("고전문학강독", 3, CourseYear.FRESHMAN, koreanDepartment);
            em.persist(course62);

            Course course63 = new Course();
            course63.createCourse("현대소설론 특강", 3, CourseYear.FRESHMAN, koreanDepartment);
            em.persist(course63);

            Course course64 = new Course();
            course64.createCourse("국어학강독", 3, CourseYear.FRESHMAN, koreanDepartment);
            em.persist(course64);

            Course course65 = new Course();
            course65.createCourse("국어음운론", 3, CourseYear.FRESHMAN, koreanDepartment);
            em.persist(course65);

            Course course66 = new Course();
            course66.createCourse("문학연구방법론", 3, CourseYear.FRESHMAN, koreanDepartment);
            em.persist(course66);

            Course course67 = new Course();
            course67.createCourse("한국현대문학사", 3, CourseYear.SOPHOMORE, koreanDepartment);
            em.persist(course67);

            Course course68 = new Course();
            course68.createCourse("국어문법론", 3, CourseYear.SOPHOMORE, koreanDepartment);
            em.persist(course68);

            Course course69 = new Course();
            course69.createCourse("국어학의 응용", 3, CourseYear.SOPHOMORE, koreanDepartment);
            em.persist(course69);

            Course course70 = new Course();
            course70.createCourse("시조가사론", 3, CourseYear.SOPHOMORE, koreanDepartment);
            em.persist(course70);

            Course course71 = new Course();
            course71.createCourse("한국 한문학의 이해", 3, CourseYear.SOPHOMORE, koreanDepartment);
            em.persist(course71);

            Course course72 = new Course();
            course72.createCourse("현대소설강독", 3, CourseYear.SOPHOMORE, koreanDepartment);
            em.persist(course72);

            Course course73 = new Course();
            course73.createCourse("국어사", 3, CourseYear.JUNIOR, koreanDepartment);
            em.persist(course73);

            Course course74 = new Course();
            course74.createCourse("국어방언학", 3, CourseYear.JUNIOR, koreanDepartment);
            em.persist(course74);

            Course course75 = new Course();
            course75.createCourse("현대시론", 3, CourseYear.JUNIOR, koreanDepartment);
            em.persist(course75);

            Course course76 = new Course();
            course76.createCourse("구비문학론", 3, CourseYear.JUNIOR, koreanDepartment);
            em.persist(course76);

            Course course77 = new Course();
            course77.createCourse("작가작품론", 3, CourseYear.JUNIOR, koreanDepartment);
            em.persist(course77);

            Course course78 = new Course();
            course78.createCourse("고전수필론", 3, CourseYear.JUNIOR, koreanDepartment);
            em.persist(course78);

            Course course79 = new Course();
            course79.createCourse("문학교육론", 3, CourseYear.JUNIOR, koreanDepartment);
            em.persist(course79);

            Course course80 = new Course();
            course80.createCourse("국어통사론", 3, CourseYear.SENIOR, koreanDepartment);
            em.persist(course80);

            Course course81 = new Course();
            course81.createCourse("한국의서사전통과 고전소설", 3, CourseYear.SENIOR, koreanDepartment);
            em.persist(course81);

            Course course82 = new Course();
            course82.createCourse("향가여요론", 3, CourseYear.SENIOR, koreanDepartment);
            em.persist(course82);

            Course course83 = new Course();
            course83.createCourse("국어의미론", 3, CourseYear.SENIOR, koreanDepartment);
            em.persist(course83);

            Course course84 = new Course();
            course84.createCourse("문예비평론", 3, CourseYear.SENIOR, koreanDepartment);
            em.persist(course84);

            Course course85 = new Course();
            course85.createCourse("현대시강독", 3, CourseYear.SENIOR, koreanDepartment);
            em.persist(course85);

            Course course86 = new Course();
            course86.createCourse("희곡론", 3, CourseYear.SENIOR, koreanDepartment);
            em.persist(course86);

            Course course87 = new Course();
            course87.createCourse("의사소통교육론", 3, CourseYear.SENIOR, koreanDepartment);
            em.persist(course87);

            Course course88 = new Course();
            course88.createCourse("중세국어문법론", 3, CourseYear.SENIOR, koreanDepartment);
            em.persist(course88);

            Course course89 = new Course();
            course89.createCourse("문학 창작론", 3, CourseYear.SENIOR, koreanDepartment);
            em.persist(course89);

            Course course90 = new Course();
            course90.createCourse("국어학사", 3, CourseYear.SENIOR, koreanDepartment);
            em.persist(course90);

            Course course91 = new Course();
            course91.createCourse("한국한문학사", 3, CourseYear.SENIOR, koreanDepartment);
            em.persist(course91);

            Course course92 = new Course();
            course92.createCourse("현대문학특강", 3, CourseYear.SENIOR, koreanDepartment);
            em.persist(course92);

            Course course93 = new Course();
            course93.createCourse("국어과 교재연구 및 지도법", 3, CourseYear.SENIOR, koreanDepartment);
            em.persist(course93);

            Course course94 = new Course();
            course94.createCourse("고전문학특강", 3, CourseYear.SENIOR, koreanDepartment);
            em.persist(course94);

            Course course95 = new Course();
            course95.createCourse("비교문학론", 3, CourseYear.SENIOR, koreanDepartment);
            em.persist(course95);

            Course course96 = new Course();
            course96.createCourse("국어어원론", 3, CourseYear.SENIOR, koreanDepartment);
            em.persist(course96);

            Course course97 = new Course();
            course97.createCourse("국어과교육론", 3, CourseYear.SENIOR, koreanDepartment);
            em.persist(course97);

            Course course98 = new Course();
            course98.createCourse("국어과 논리 및 논술", 3, CourseYear.SENIOR, koreanDepartment);
            em.persist(course98);

            Course course99 = new Course();
            course99.createCourse("중국어와 중국문화", 3, CourseYear.FRESHMAN, chineseDepartment);
            em.persist(course99);

            Course course100 = new Course();
            course100.createCourse("기초중국어회화1", 3, CourseYear.FRESHMAN, chineseDepartment);
            em.persist(course100);

            Course course101 = new Course();
            course101.createCourse("중국어와 중국사회", 3, CourseYear.FRESHMAN, chineseDepartment);
            em.persist(course101);

            Course course102 = new Course();
            course102.createCourse("기초중국어회화2", 3, CourseYear.FRESHMAN, chineseDepartment);
            em.persist(course102);

            Course course103 = new Course();
            course103.createCourse("현대 한자학의 이해", 3, CourseYear.FRESHMAN, chineseDepartment);
            em.persist(course103);

            Course course104 = new Course();
            course104.createCourse("중급중국어1", 3, CourseYear.SOPHOMORE, chineseDepartment);
            em.persist(course104);

            Course course105 = new Course();
            course105.createCourse("중국어 회화1", 3, CourseYear.SOPHOMORE, chineseDepartment);
            em.persist(course105);

            Course course106 = new Course();
            course106.createCourse("경서제자입문", 3, CourseYear.SOPHOMORE, chineseDepartment);
            em.persist(course106);

            Course course107 = new Course();
            course107.createCourse("중국어학의 이해", 3, CourseYear.SOPHOMORE, chineseDepartment);
            em.persist(course107);

            Course course108 = new Course();
            course108.createCourse("현대 중국의 이해", 3, CourseYear.SOPHOMORE, chineseDepartment);
            em.persist(course108);

            Course course109 = new Course();
            course109.createCourse("중급중국어2", 3, CourseYear.SOPHOMORE, chineseDepartment);
            em.persist(course109);

            Course course110 = new Course();
            course110.createCourse("역대산문의감상과이해", 3, CourseYear.SOPHOMORE, chineseDepartment);
            em.persist(course110);

            Course course111 = new Course();
            course111.createCourse("중국 언어와 문화의 이해", 3, CourseYear.SOPHOMORE, chineseDepartment);
            em.persist(course111);

            Course course112 = new Course();
            course112.createCourse("중국어회화2", 3, CourseYear.SOPHOMORE, chineseDepartment);
            em.persist(course112);

            Course course113 = new Course();
            course113.createCourse("중국어문법1", 3, CourseYear.SOPHOMORE, chineseDepartment);
            em.persist(course113);

            Course course114 = new Course();
            course114.createCourse("중국의 시와 노래1", 3, CourseYear.JUNIOR, chineseDepartment);
            em.persist(course114);

            Course course115 = new Course();
            course115.createCourse("중국의 매스미디어와 현대 사회", 3, CourseYear.JUNIOR, chineseDepartment);
            em.persist(course115);

            Course course116 = new Course();
            course116.createCourse("소설로 보는 중국 사회1", 3, CourseYear.JUNIOR, chineseDepartment);
            em.persist(course116);

            Course course117 = new Course();
            course117.createCourse("중국의 비즈니스 문화와 커뮤니케이션", 3, CourseYear.JUNIOR, chineseDepartment);
            em.persist(course117);

            Course course118 = new Course();
            course118.createCourse("중국어문법2", 3, CourseYear.JUNIOR, chineseDepartment);
            em.persist(course118);

            Course course119 = new Course();
            course119.createCourse("중국 문학과 문화의 이해", 3, CourseYear.JUNIOR, chineseDepartment);
            em.persist(course119);

            Course course120 = new Course();
            course120.createCourse("중국현대문학 특강", 3, CourseYear.JUNIOR, chineseDepartment);
            em.persist(course120);

            Course course121 = new Course();
            course121.createCourse("중국의 시와 노래2", 3, CourseYear.JUNIOR, chineseDepartment);
            em.persist(course121);

            Course course122 = new Course();
            course122.createCourse("통합적 중국어 글쓰기", 3, CourseYear.JUNIOR, chineseDepartment);
            em.persist(course122);

            Course course123 = new Course();
            course123.createCourse("중국 지역사회와 영화", 3, CourseYear.JUNIOR, chineseDepartment);
            em.persist(course123);

            Course course124 = new Course();
            course124.createCourse("소설로 보는 중국 사회Ⅱ", 3, CourseYear.JUNIOR, chineseDepartment);
            em.persist(course124);

            Course course125 = new Course();
            course125.createCourse("중어과교육론", 3, CourseYear.JUNIOR, chineseDepartment);
            em.persist(course125);

            Course course126 = new Course();
            course126.createCourse("비즈니스 중국어", 3, CourseYear.SENIOR, chineseDepartment);
            em.persist(course126);

            Course course127 = new Course();
            course127.createCourse("중국어통번역연습", 3, CourseYear.SENIOR, chineseDepartment);
            em.persist(course127);

            Course course128 = new Course();
            course128.createCourse("중국의 고전 가곡과 연극", 3, CourseYear.SENIOR, chineseDepartment);
            em.persist(course128);

            Course course129 = new Course();
            course129.createCourse("중국어 현장실습", 3, CourseYear.SENIOR, chineseDepartment);
            em.persist(course129);

            Course course130 = new Course();
            course130.createCourse("중어과교재연구 및 지도법", 3, CourseYear.SENIOR, chineseDepartment);
            em.persist(course130);

            Course course131 = new Course();
            course131.createCourse("중국문학특강", 3, CourseYear.SENIOR, chineseDepartment);
            em.persist(course131);

            Course course132 = new Course();
            course132.createCourse("중국 지역학 세미나", 3, CourseYear.SENIOR, chineseDepartment);
            em.persist(course132);

            Course course133 = new Course();
            course133.createCourse("대중매체로 보는 당대 중국", 3, CourseYear.SENIOR, chineseDepartment);
            em.persist(course133);

            Course course134 = new Course();
            course134.createCourse("중국어 논리 및 논술", 3, CourseYear.SENIOR, chineseDepartment);
            em.persist(course134);

            Course course135 = new Course();
            course135.createCourse("영미문학 배경", 3, CourseYear.FRESHMAN, koreanDepartment);
            em.persist(course135);

            Course course136 = new Course();
            course136.createCourse("영문법", 3, CourseYear.FRESHMAN, koreanDepartment);
            em.persist(course136);

            Course course137 = new Course();
            course137.createCourse("영작문Ⅰ", 3, CourseYear.FRESHMAN, koreanDepartment);
            em.persist(course137);

            Course course138 = new Course();
            course138.createCourse("영어회화Ⅰ", 3, CourseYear.FRESHMAN, koreanDepartment);
            em.persist(course138);

            Course course139 = new Course();
            course139.createCourse("영어음성학", 3, CourseYear.FRESHMAN, koreanDepartment);
            em.persist(course139);

            Course course140 = new Course();
            course140.createCourse("영미소설의 이해", 3, CourseYear.FRESHMAN, koreanDepartment);
            em.persist(course140);

            Course course141 = new Course();
            course141.createCourse("문학 번역 연습", 3, CourseYear.FRESHMAN, koreanDepartment);
            em.persist(course141);

            Course course142 = new Course();
            course142.createCourse("영문학사", 3, CourseYear.FRESHMAN, koreanDepartment);
            em.persist(course142);

            Course course143 = new Course();
            course143.createCourse("영어듣기", 3, CourseYear.FRESHMAN, koreanDepartment);
            em.persist(course143);

            Course course144 = new Course();
            course144.createCourse("영작문Ⅱ", 3, CourseYear.SOPHOMORE, koreanDepartment);
            em.persist(course144);

            Course course145 = new Course();
            course145.createCourse("영어회화Ⅱ", 3, CourseYear.SOPHOMORE, koreanDepartment);
            em.persist(course145);

            Course course146 = new Course();
            course146.createCourse("현대 영어학의 이해", 3, CourseYear.SOPHOMORE, koreanDepartment);
            em.persist(course146);

            Course course147 = new Course();
            course147.createCourse("영어토론연습", 3, CourseYear.SOPHOMORE, koreanDepartment);
            em.persist(course147);

            Course course148 = new Course();
            course148.createCourse("영미희곡의 이해", 3, CourseYear.SOPHOMORE, koreanDepartment);
            em.persist(course148);

            Course course149 = new Course();
            course149.createCourse("영어 어휘 연습", 3, CourseYear.JUNIOR, koreanDepartment);
            em.persist(course149);

            Course course150 = new Course();
            course150.createCourse("영미시의 이해", 3, CourseYear.JUNIOR, koreanDepartment);
            em.persist(course150);

            Course course151 = new Course();
            course151.createCourse("영미문학비평", 3, CourseYear.JUNIOR, koreanDepartment);
            em.persist(course151);

            Course course152 = new Course();
            course152.createCourse("영어통사론", 3, CourseYear.JUNIOR, koreanDepartment);
            em.persist(course152);

            Course course153 = new Course();
            course153.createCourse("영어 통사론의 이해", 3, CourseYear.JUNIOR, koreanDepartment);
            em.persist(course153);

            Course course154 = new Course();
            course154.createCourse("아동 및 청소년 문학", 3, CourseYear.JUNIOR, koreanDepartment);
            em.persist(course154);

            Course course155 = new Course();
            course155.createCourse("실무영어", 3, CourseYear.JUNIOR, koreanDepartment);
            em.persist(course155);

            Course course156 = new Course();
            course156.createCourse("영어사", 3, CourseYear.SENIOR, koreanDepartment);
            em.persist(course156);

            Course course157 = new Course();
            course157.createCourse("셰익스피어", 3, CourseYear.SENIOR, koreanDepartment);
            em.persist(course157);

            Course course158 = new Course();
            course158.createCourse("현대영미시", 3, CourseYear.SENIOR, koreanDepartment);
            em.persist(course158);

            Course course159 = new Course();
            course159.createCourse("영미소설", 3, CourseYear.SENIOR, koreanDepartment);
            em.persist(course159);

            Course course160 = new Course();
            course160.createCourse("영어논술 연습", 3, CourseYear.SENIOR, koreanDepartment);
            em.persist(course160);

            Course course161 = new Course();
            course161.createCourse("영어회화Ⅲ", 3, CourseYear.SENIOR, koreanDepartment);
            em.persist(course161);

            Course course162 = new Course();
            course162.createCourse("시사영어강독", 3, CourseYear.SENIOR, koreanDepartment);
            em.persist(course162);

            Course course163 = new Course();
            course163.createCourse("실용영문법 특강", 3, CourseYear.SENIOR, koreanDepartment);
            em.persist(course163);

            Course course164 = new Course();
            course164.createCourse("영미 드라마 번역", 3, CourseYear.SENIOR, koreanDepartment);
            em.persist(course164);

            Course course165 = new Course();
            course165.createCourse("영미시 세미나", 3, CourseYear.SENIOR, koreanDepartment);
            em.persist(course165);

            Course course166 = new Course();
            course166.createCourse("영어과 교재연구 및 지도법", 3, CourseYear.SENIOR, koreanDepartment);
            em.persist(course166);

            Course course167 = new Course();
            course167.createCourse("통번역 세미나", 3, CourseYear.SENIOR, koreanDepartment);
            em.persist(course167);

            Course course168 = new Course();
            course168.createCourse("미국문학사", 3, CourseYear.SENIOR, koreanDepartment);
            em.persist(course168);

            Course course169 = new Course();
            course169.createCourse("영어학세미나", 3, CourseYear.SENIOR, koreanDepartment);
            em.persist(course169);

            Course course170 = new Course();
            course170.createCourse("영문학특강", 3, CourseYear.SENIOR, koreanDepartment);
            em.persist(course170);

            Course course171 = new Course();
            course171.createCourse("영어과 교육론", 3, CourseYear.SENIOR, koreanDepartment);
            em.persist(course171);

            Course course172 = new Course();
            course172.createCourse("독일어 회화Ⅰ", 3, CourseYear.FRESHMAN, germanDepartment);
            em.persist(course172);

            Course course173 = new Course();
            course173.createCourse("독일어 연습Ⅰ", 3, CourseYear.FRESHMAN, germanDepartment);
            em.persist(course173);

            Course course174 = new Course();
            course174.createCourse("독일문학 산책", 3, CourseYear.FRESHMAN, germanDepartment);
            em.persist(course174);

            Course course175 = new Course();
            course175.createCourse("독일어 회화Ⅱ", 3, CourseYear.SOPHOMORE, germanDepartment);
            em.persist(course175);

            Course course176 = new Course();
            course176.createCourse("기초 독문법", 3, CourseYear.SOPHOMORE, germanDepartment);
            em.persist(course176);

            Course course177 = new Course();
            course177.createCourse("독일어 연습Ⅱ", 3, CourseYear.SOPHOMORE, germanDepartment);
            em.persist(course177);

            Course course178 = new Course();
            course178.createCourse("독일어 회화Ⅲ", 3, CourseYear.JUNIOR, germanDepartment);
            em.persist(course178);

            Course course179 = new Course();
            course179.createCourse("독일어 연습Ⅲ", 3, CourseYear.JUNIOR, germanDepartment);
            em.persist(course179);

            Course course180 = new Course();
            course180.createCourse("중급독문법", 3, CourseYear.JUNIOR, germanDepartment);
            em.persist(course180);

            Course course181 = new Course();
            course181.createCourse("독일어 문장 구조", 3, CourseYear.JUNIOR, germanDepartment);
            em.persist(course181);

            Course course182 = new Course();
            course182.createCourse("독일문화 탐방", 3, CourseYear.JUNIOR, germanDepartment);
            em.persist(course182);

            Course course183 = new Course();
            course183.createCourse("독일의 지리와 역사", 3, CourseYear.JUNIOR, germanDepartment);
            em.persist(course183);

            Course course184 = new Course();
            course184.createCourse("독일 아동·청소년 교육과 문학", 3, CourseYear.JUNIOR, germanDepartment);
            em.persist(course184);

            Course course185 = new Course();
            course185.createCourse("독일 문예사조의 이해", 3, CourseYear.SENIOR, germanDepartment);
            em.persist(course185);

            Course course186 = new Course();
            course186.createCourse("독일어 회화Ⅳ", 3, CourseYear.SENIOR, germanDepartment);
            em.persist(course186);

            Course course187 = new Course();
            course187.createCourse("독일어 연습Ⅳ", 3, CourseYear.SENIOR, germanDepartment);
            em.persist(course187);

            Course course188 = new Course();
            course188.createCourse("독일시와 음악", 3, CourseYear.SENIOR, germanDepartment);
            em.persist(course188);

            Course course189 = new Course();
            course189.createCourse("B1 텍스트 읽기", 3, CourseYear.SENIOR, germanDepartment);
            em.persist(course189);

            Course course190 = new Course();
            course190.createCourse("독일작가읽기", 3, CourseYear.SENIOR, germanDepartment);
            em.persist(course190);

            Course course191 = new Course();
            course191.createCourse("독일의 정치와 사회", 3, CourseYear.SENIOR, germanDepartment);
            em.persist(course191);

            Course course192 = new Course();
            course192.createCourse("B2 텍스트 읽기", 3, CourseYear.SENIOR, germanDepartment);
            em.persist(course192);

            Course course193 = new Course();
            course193.createCourse("독일드라마와 공연예술", 3, CourseYear.SENIOR, germanDepartment);
            em.persist(course193);

            Course course194 = new Course();
            course194.createCourse("독일문학과 사회 비평", 3, CourseYear.SENIOR, germanDepartment);
            em.persist(course194);

            Course course195 = new Course();
            course195.createCourse("독일의 언어정책과 통상전략", 3, CourseYear.SENIOR, germanDepartment);
            em.persist(course195);

            Course course196 = new Course();
            course196.createCourse("고급 독일어 회화 및 작문", 3, CourseYear.SENIOR, germanDepartment);
            em.persist(course196);

            Course course197 = new Course();
            course197.createCourse("독일어 관용구 표현과 의미", 3, CourseYear.SENIOR, germanDepartment);
            em.persist(course197);

            Course course198 = new Course();
            course198.createCourse("독일영화와 독일사회", 3, CourseYear.SENIOR, germanDepartment);
            em.persist(course198);

            Course course199 = new Course();
            course199.createCourse("독일통일과 유럽", 3, CourseYear.SENIOR, germanDepartment);
            em.persist(course199);

            Course course200 = new Course();
            course200.createCourse("독일 현대문학의 이해", 3, CourseYear.SENIOR, germanDepartment);
            em.persist(course200);

            Course course201 = new Course();
            course201.createCourse("독일어 논술 연습", 3, CourseYear.SENIOR, germanDepartment);
            em.persist(course201);

            Course course202 = new Course();
            course202.createCourse("독일어와 한국어 비교", 3, CourseYear.SENIOR, germanDepartment);
            em.persist(course202);

            Course course203 = new Course();
            course203.createCourse("독일지역학 세미나", 3, CourseYear.SENIOR, germanDepartment);
            em.persist(course203);

            Course course204 = new Course();
            course204.createCourse("독일어 교육론", 3, CourseYear.SENIOR, germanDepartment);
            em.persist(course204);

            Course course205 = new Course();
            course205.createCourse("독일 철학과 현대유럽", 3, CourseYear.SENIOR, germanDepartment);
            em.persist(course205);

            Course course206 = new Course();
            course206.createCourse("독일의 지성사", 3, CourseYear.SENIOR, germanDepartment);
            em.persist(course206);

            Course course207 = new Course();
            course207.createCourse("독일의 기업과 경제", 3, CourseYear.SENIOR, germanDepartment);
            em.persist(course207);

            Course course208 = new Course();
            course208.createCourse("독일소설", 3, CourseYear.SENIOR, germanDepartment);
            em.persist(course208);

            Course course209 = new Course();
            course209.createCourse("독일어 교재연구 및 지도법", 3, CourseYear.SENIOR, germanDepartment);
            em.persist(course209);

            UserCourse usercourse1 = new UserCourse();
            usercourse1.createUserCourse(
                    user2, course2, GradeStatus.APLUS, IsMajor.MAJOR
            );
            em.persist(usercourse1);

            UserCourse usercourse2 = new UserCourse();
            usercourse2.createUserCourse(
                    user3, course2, GradeStatus.A, IsMajor.MAJOR
            );
            em.persist(usercourse2);

            UserCourse usercourse3 = new UserCourse();
            usercourse3.createUserCourse(
                    user1, course19, GradeStatus.B, IsMajor.MAJOR
            );
            em.persist(usercourse3);

            UserCourse usercourse4 = new UserCourse();
            usercourse4.createUserCourse(
                    user2, course19, GradeStatus.APLUS, IsMajor.MAJOR
            );
            em.persist(usercourse4);

            UserCourse usercourse5 = new UserCourse();
            usercourse5.createUserCourse(
                    user3, course3, GradeStatus.APLUS, IsMajor.MAJOR
            );
            em.persist(usercourse5);

            UserCourse usercourse6 = new UserCourse();
            usercourse6.createUserCourse(
                    user3, course19, GradeStatus.APLUS, IsMajor.MAJOR
            );
            em.persist(usercourse6);

            UserCourse usercourse7 = new UserCourse();
            usercourse7.createUserCourse(
                    user3, course19, GradeStatus.A, IsMajor.MAJOR
            );
            em.persist(usercourse7);

            UserCourse usercourse8 = new UserCourse();
            usercourse8.createUserCourse(
                    user4, course19, GradeStatus.B, IsMajor.MAJOR
            );
            em.persist(usercourse8);

            UserCourse usercourse9 = new UserCourse();
            usercourse9.createUserCourse(
                    user5, course19, GradeStatus.BPLUS, IsMajor.MAJOR
            );
            em.persist(usercourse9);

            UserCourse usercourse10 = new UserCourse();
            usercourse10.createUserCourse(
                    user6, course19, GradeStatus.APLUS, IsMajor.MAJOR
            );
            em.persist(usercourse10);

            UserCourse usercourse11 = new UserCourse();
            usercourse10.createUserCourse(
                    user7, course19, GradeStatus.C, IsMajor.MAJOR
            );
            em.persist(usercourse11);



        }
    }

}
