package com.mentormentee.core;

import com.mentormentee.core.domain.*;
import com.mentormentee.core.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static com.mentormentee.core.domain.Role.ROLE_MENTEE;
import static com.mentormentee.core.domain.Role.ROLE_MENTOR;
import static com.mentormentee.core.domain.WaysOfCommunication.FACETOFACE;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;


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
            review.createReview(user3, 3, "좋은 사람 근데 가끔 냄새나요");
            Review review1 = new Review();
            review1.createDate(2024,3,2);
            review1.createReview(user3, 5, "친절헤여");
            AvailableTime availableTime1 = new AvailableTime(user3, DayOfWeek.FRIDAY, LocalTime.of(9, 0), LocalTime.of(14, 0));
            AvailableTime availableTime2 = new AvailableTime(user3, DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(14, 0));
            em.persist(user3);
            em.persist(availableTime1);
            em.persist(availableTime2);
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

            Course course1 = new Course();
            course1.createCourse("디지털공학", 3, "최준성", CourseYear.SOPHOMORE, informationCommunicationDepartment);
            em.persist(course1);

            Course course2 = new Course();
            course2.createCourse( "공학수학1", 3, "심동규", CourseYear.SOPHOMORE, informationCommunicationDepartment);
            em.persist(course2);

            Course course3 = new Course();
            course3.createCourse( "영어수업", 3, "심동규", CourseYear.SOPHOMORE, informationCommunicationDepartment);
            em.persist(course3);

            Course course4 = new Course();
            course4.createCourse( "공학수학2", 3, "심동규", CourseYear.SOPHOMORE, informationCommunicationDepartment);
            em.persist(course4);

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
                    user1, course2, GradeStatus.B, IsMajor.MAJOR
            );
            em.persist(usercourse3);

            UserCourse usercourse4 = new UserCourse();
            usercourse4.createUserCourse(
                    user2, course1, GradeStatus.APLUS, IsMajor.MAJOR
            );
            em.persist(usercourse4);

            UserCourse usercourse5 = new UserCourse();
            usercourse5.createUserCourse(
                    user3, course3, GradeStatus.APLUS, IsMajor.MAJOR
            );
            em.persist(usercourse5);

            UserCourse usercourse6 = new UserCourse();
            usercourse6.createUserCourse(
                    user3, course4, GradeStatus.APLUS, IsMajor.MAJOR
            );
            em.persist(usercourse6);

        }
    }

}
