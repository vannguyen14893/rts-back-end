package com.cmc.recruitment.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.cmc.recruitment.entity.CandidateStatus;
import com.cmc.recruitment.entity.CvStatus;
import com.cmc.recruitment.entity.Department;
import com.cmc.recruitment.entity.Experience;
import com.cmc.recruitment.entity.ForeignLanguage;
import com.cmc.recruitment.entity.InterviewStatus;
import com.cmc.recruitment.entity.RecruitmentType;
import com.cmc.recruitment.entity.Role;
import com.cmc.recruitment.entity.Skill;
import com.cmc.recruitment.repository.CandidateStatusRepository;
import com.cmc.recruitment.repository.CvStatusRepository;
import com.cmc.recruitment.repository.DepartmentRepository;
import com.cmc.recruitment.repository.ExperienceRepository;
import com.cmc.recruitment.repository.ForeignLanguageRepository;
import com.cmc.recruitment.repository.InterviewStatusRepository;
import com.cmc.recruitment.repository.RecruitmentTypeRepository;
import com.cmc.recruitment.repository.RoleRepository;
import com.cmc.recruitment.repository.SkillRepository;
import com.cmc.recruitment.repository.UserRepository;

@Component
public class DataSeedingListener implements ApplicationListener<ContextRefreshedEvent> {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private DepartmentRepository departmentrepository;

  @Autowired
  private InterviewStatusRepository interviewStatusRepository;

  @Autowired
  private SkillRepository skillRepository;

  @Autowired
  private CvStatusRepository cvStatusRepository;

  @Autowired
  private CandidateStatusRepository candidateStatusRepository;

  @Autowired
  private ExperienceRepository experienceRepository;

  @Autowired
  private ForeignLanguageRepository foreignLanguageRepository;

  @Autowired
  private RecruitmentTypeRepository recruitmentTypeRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public void onApplicationEvent(ContextRefreshedEvent arg0) {

    // Department dump database
    if (departmentrepository.findByTitle("IT") == null) {
      departmentrepository.save(new Department("IT", "It support"));
    }

    if (departmentrepository.findByTitle("DU1") == null) {
      departmentrepository.save(new Department("DU1", "DU1"));
    }

    if (departmentrepository.findByTitle("DU2") == null) {
      departmentrepository.save(new Department("DU2", "DU2"));
    }

    if (departmentrepository.findByTitle("DU3") == null) {
      departmentrepository.save(new Department("DU3", "DU3"));
    }

    if (departmentrepository.findByTitle("RRC") == null) {
      departmentrepository.save(new Department("RRC", "RRC"));
    }

    if (departmentrepository.findByTitle("BU1") == null) {
      departmentrepository.save(new Department("BU1", "BU1"));
    }

    if (departmentrepository.findByTitle("BU2") == null) {
      departmentrepository.save(new Department("BU2", "BU2"));
    }
    
    if (departmentrepository.findByTitle("BU3") == null) {
        departmentrepository.save(new Department("BU3", "BU3"));
      }

    if (departmentrepository.findByTitle("HR") == null) {
      departmentrepository.save(new Department("HR", "HR"));
    }

    // Roles dump database
    if (roleRepository.findByRoleName("ROLE_ADMIN") == null) {
      roleRepository.save(new Role("ROLE_ADMIN"));
    }

    if (roleRepository.findByRoleName("ROLE_GROUP_LEAD") == null) {
      roleRepository.save(new Role("ROLE_GROUP_LEAD"));
    }

    if (roleRepository.findByRoleName("ROLE_DU_LEAD") == null) {
      roleRepository.save(new Role("ROLE_DU_LEAD"));
    }

    if (roleRepository.findByRoleName("ROLE_DU_MEMBER") == null) {
      roleRepository.save(new Role("ROLE_DU_MEMBER"));
    }

    if (roleRepository.findByRoleName("ROLE_HR_MANAGER") == null) {
      roleRepository.save(new Role("ROLE_HR_MANAGER"));
    }

    if (roleRepository.findByRoleName("ROLE_HR_MEMBER") == null) {
      roleRepository.save(new Role("ROLE_HR_MEMBER"));
    }

    // User account dump database
//    if (userRepository.findByEmail("admin@gmail.com") == null) {
//      User adminht = new User();
//      adminht.setUsername("admin");
//      adminht.setDepartmentId(departmentrepository.findByTitle("IT"));
//      adminht.setEmail("admin@gmail.com");
////      adminht.setAvatarUrl(
////          "https://scontent.fhan1-1.fna.fbcdn.net/v/t1.0-1/p160x160/24294387_10208399272817805_4802968356693395915_n.jpg?oh=bca5ae2d914cffc4ef8e52c0d1a81bb0&oe=5B440B35");
//      adminht.setFullName("Admin System");
//      adminht.setIsActive(true);
//      adminht.setPassword(passwordEncoder.encode("12345678"));
//      HashSet<Role> roles = new HashSet<>();
//      roles.add(roleRepository.findByRoleName("ROLE_ADMIN"));
//      adminht.setRoleCollection(roles);
//      userRepository.save(adminht);
//    }
//
//    // dulead account
//    if (userRepository.findByEmail("hrlead@gmail.com") == null) {
//      User hrlead = new User();
//      hrlead.setUsername("hrlead");
//      hrlead.setDepartmentId(departmentrepository.findByTitle("HR"));
//      hrlead.setEmail("hrlead@gmail.com");
////      dulead.setAvatarUrl(
////          "https://scontent.fhan1-1.fna.fbcdn.net/v/t1.0-1/p160x160/24294387_10208399272817805_4802968356693395915_n.jpg?oh=bca5ae2d914cffc4ef8e52c0d1a81bb0&oe=5B440B35");
//      hrlead.setFullName("Hr Lead");
//      hrlead.setIsActive(true);
//      hrlead.setPassword(passwordEncoder.encode("12345678"));
//      HashSet<Role> roles = new HashSet<>();
//      roles.add(roleRepository.findByRoleName("ROLE_DU_LEAD"));
//      hrlead.setRoleCollection(roles);
//      userRepository.save(hrlead);
//    }
//    // du member
////    if (userRepository.findByEmail("dumember@gmail.com") == null) {
////      User dumember = new User();
////      dumember.setUsername("dumember");
////      dumember.setDepartmentId(departmentrepository.findByTitle("DU1"));
////      dumember.setEmail("dumember@gmail.com");
////      dumember.setAvatarUrl(
////          "https://scontent.fhan1-1.fna.fbcdn.net/v/t1.0-1/p160x160/24294387_10208399272817805_4802968356693395915_n.jpg?oh=bca5ae2d914cffc4ef8e52c0d1a81bb0&oe=5B440B35");
////      dumember.setFullName("Du member");
////      dumember.setIsActive(true);
////      dumember.setPassword(passwordEncoder.encode("12345678"));
////      HashSet<Role> roles = new HashSet<>();
////      roles.add(roleRepository.findByRoleName("ROLE_DU_MEMBER"));
////      dumember.setRoleCollection(roles);
////      userRepository.save(dumember);
////    }
//
//    // hr manager
//    if (userRepository.findByEmail("ntnhan@cmc.com.vn") == null) {
//      User hrmanager = new User();
//      hrmanager.setUsername("ntnhan");
//      hrmanager.setDepartmentId(departmentrepository.findByTitle("HR"));
//      hrmanager.setEmail("ntnhan@cmc.com.vn");
////      hrmanager.setAvatarUrl(
////          "https://scontent.fhan1-1.fna.fbcdn.net/v/t1.0-1/p160x160/24294387_10208399272817805_4802968356693395915_n.jpg?oh=bca5ae2d914cffc4ef8e52c0d1a81bb0&oe=5B440B35");
//      hrmanager.setFullName("Nguyễn Thanh Nhàn");
//      hrmanager.setIsActive(true);
//      hrmanager.setPassword(passwordEncoder.encode("12345678"));
//      HashSet<Role> roles = new HashSet<>();
//      roles.add(roleRepository.findByRoleName("ROLE_HR_MANAGER"));
//      hrmanager.setRoleCollection(roles);
//      userRepository.save(hrmanager);
//    }
//
//    // hr member1
//    if (userRepository.findByEmail("ntanh11@cmc.com.vn") == null) {
//      User hrmember = new User();
//      hrmember.setUsername("ntanh11");
//      hrmember.setDepartmentId(departmentrepository.findByTitle("HR"));
//      hrmember.setEmail("ntanh11@cmc.com.vn");
////      hrmember.setAvatarUrl(
////          "https://scontent.fhan1-1.fna.fbcdn.net/v/t1.0-1/p160x160/24294387_10208399272817805_4802968356693395915_n.jpg?oh=bca5ae2d914cffc4ef8e52c0d1a81bb0&oe=5B440B35");
//      hrmember.setFullName("Ngô Thị Ánh");
//      hrmember.setIsActive(true);
//      hrmember.setPassword(passwordEncoder.encode("12345678"));
//      HashSet<Role> roles = new HashSet<>();
//      roles.add(roleRepository.findByRoleName("ROLE_HR_MEMBER"));
//      hrmember.setRoleCollection(roles);
//      userRepository.save(hrmember);
//    }
//    
// // hr member2
//    if (userRepository.findByEmail("vthnhung1@cmc.com.vn") == null) {
//      User hrmember = new User();
//      hrmember.setUsername("vthnhung1");
//      hrmember.setDepartmentId(departmentrepository.findByTitle("HR"));
//      hrmember.setEmail("vthnhung1@cmc.com.vn");
////      hrmember.setAvatarUrl(
////          "https://scontent.fhan1-1.fna.fbcdn.net/v/t1.0-1/p160x160/24294387_10208399272817805_4802968356693395915_n.jpg?oh=bca5ae2d914cffc4ef8e52c0d1a81bb0&oe=5B440B35");
//      hrmember.setFullName("Vũ Thị Hồng Nhung");
//      hrmember.setIsActive(true);
//      hrmember.setPassword(passwordEncoder.encode("12345678"));
//      HashSet<Role> roles = new HashSet<>();
//      roles.add(roleRepository.findByRoleName("ROLE_HR_MEMBER"));
//      hrmember.setRoleCollection(roles);
//      userRepository.save(hrmember);
//    }
//    
// // hr member3
//    if (userRepository.findByEmail("ptnanh@cmc.com.vn") == null) {
//      User hrmember = new User();
//      hrmember.setUsername("ptnanh");
//      hrmember.setDepartmentId(departmentrepository.findByTitle("HR"));
//      hrmember.setEmail("ptnanh@cmc.com.vn");
////      hrmember.setAvatarUrl(
////          "https://scontent.fhan1-1.fna.fbcdn.net/v/t1.0-1/p160x160/24294387_10208399272817805_4802968356693395915_n.jpg?oh=bca5ae2d914cffc4ef8e52c0d1a81bb0&oe=5B440B35");
//      hrmember.setFullName("Phạm Thị Ngọc Anh");
//      hrmember.setIsActive(true);
//      hrmember.setPassword(passwordEncoder.encode("12345678"));
//      HashSet<Role> roles = new HashSet<>();
//      roles.add(roleRepository.findByRoleName("ROLE_HR_MEMBER"));
//      hrmember.setRoleCollection(roles);
//      userRepository.save(hrmember);
//    }
//
//    // hr member4
//    if (userRepository.findByEmail("ttpthao2@cmc.com.vn") == null) {
//      User hrmember = new User();
//      hrmember.setUsername("ttpthao2");
//      hrmember.setDepartmentId(departmentrepository.findByTitle("HR"));
//      hrmember.setEmail("ttpthao2@cmc.com.vn");
////      hrmember.setAvatarUrl(
////          "https://scontent.fhan1-1.fna.fbcdn.net/v/t1.0-1/p160x160/24294387_10208399272817805_4802968356693395915_n.jpg?oh=bca5ae2d914cffc4ef8e52c0d1a81bb0&oe=5B440B35");
//      hrmember.setFullName("Thiều Thị Phương Thảo");
//      hrmember.setIsActive(true);
//      hrmember.setPassword(passwordEncoder.encode("12345678"));
//      HashSet<Role> roles = new HashSet<>();
//      roles.add(roleRepository.findByRoleName("ROLE_HR_MEMBER"));
//      hrmember.setRoleCollection(roles);
//      userRepository.save(hrmember);
//    }
//    // RRC Lead
//    if (userRepository.findByEmail("dnbao@cmc.com.vn") == null) {
//      User rrclead = new User();
//      rrclead.setUsername("dnbao");
//      rrclead.setDepartmentId(departmentrepository.findByTitle("RRC"));
//      rrclead.setEmail("dnbao@cmc.com.vn");
//      rrclead.setAvatarUrl(
//          "https://scontent.fhan1-1.fna.fbcdn.net/v/t1.0-1/p160x160/24294387_10208399272817805_4802968356693395915_n.jpg?oh=bca5ae2d914cffc4ef8e52c0d1a81bb0&oe=5B440B35");
//      rrclead.setFullName("Rrc Lead");
//      rrclead.setIsActive(true);
//      rrclead.setPassword(passwordEncoder.encode("12345678"));
//      HashSet<Role> roles = new HashSet<>();
//      roles.add(roleRepository.findByRoleName("ROLE_RRC_LEAD"));
//      rrclead.setRoleCollection(roles);
//      userRepository.save(rrclead);
//    }

    // dump database table position
//    if (positionRepository.findByTitle("DEV") == null) {
//      positionRepository.save(new Position("DEV", "Developer IT"));
//    }
//
//    if (positionRepository.findByTitle("Test Manual") == null) {
//      positionRepository.save(new Position("Test Manual", "Test manual IT"));
//    }
//
//    if (positionRepository.findByTitle("Test Auto") == null) {
//      positionRepository.save(new Position("Test Auto", "Test Auto IT"));
//    }
//
//    if (positionRepository.findByTitle("PM") == null) {
//      positionRepository.save(new Position("PM", "PM IT"));
//    }
//
//    if (positionRepository.findByTitle("BA") == null) {
//      positionRepository.save(new Position("BA", "BA IT"));
//    }
//
//    if (positionRepository.findByTitle("SA") == null) {
//      positionRepository.save(new Position("SA", "SA IT"));
//    }

    // dump database table InterviewStatus
    if (interviewStatusRepository.findByTitle("New") == null) {
      interviewStatusRepository.save(new InterviewStatus("New", "New interview status"));
    }

    if (interviewStatusRepository.findByTitle("In-Process") == null) {
      interviewStatusRepository
          .save(new InterviewStatus("In-Process", "In-Process interview status"));
    }

    if (interviewStatusRepository.findByTitle("Done") == null) {
      interviewStatusRepository.save(new InterviewStatus("Done", "Done interview status"));
    }

    // dump database table Skill

    if (skillRepository.findByTitle("Java") == null) {
      skillRepository.save(new Skill("Java", "Java"));
    }

    if (skillRepository.findByTitle("C#") == null) {
      skillRepository.save(new Skill("C#", "C#"));
    }

    if (skillRepository.findByTitle("C++") == null) {
      skillRepository.save(new Skill("C++", "C++"));
    }

    if (skillRepository.findByTitle("PHP") == null) {
      skillRepository.save(new Skill("PHP", "PHP"));
    }

    if (skillRepository.findByTitle("Android") == null) {
      skillRepository.save(new Skill("Android", "Android"));
    }

    // dump data table CvStatus

    if (cvStatusRepository.findByTitle("Sourced") == null) {
      cvStatusRepository.save(new CvStatus("Sourced", "Sourced"));
    }

    if (cvStatusRepository.findByTitle("BlackList") == null) {
      cvStatusRepository.save(new CvStatus("BlackList", "BlackList"));
    }

    // dump data table CandidateStatus

    if (candidateStatusRepository.findByTitle("Apply") == null) {
      candidateStatusRepository.save(new CandidateStatus("Apply", "Apply"));
    }

    if (candidateStatusRepository.findByTitle("Contacting") == null) {
      candidateStatusRepository.save(new CandidateStatus("Contacting", "Contacting"));
    }

    if (candidateStatusRepository.findByTitle("Interview ") == null) {
      candidateStatusRepository.save(new CandidateStatus("Interview ", "Interview "));
    }

    if (candidateStatusRepository.findByTitle("Offer") == null) {
      candidateStatusRepository.save(new CandidateStatus("Offer", "Offer"));
    }

    if (candidateStatusRepository.findByTitle("Onboard") == null) {
      candidateStatusRepository.save(new CandidateStatus("Onboard", "Onboard"));
    }

    if (candidateStatusRepository.findByTitle("Closed") == null) {
      candidateStatusRepository.save(new CandidateStatus("Closed", "Closed"));
    }

    // dump data table project

//    if (projectRepository.findByTitle("RTS") == null) {
//      projectRepository.save(new Project("RTS", "RTS"));
//    }


    // dump data table experience

    if (experienceRepository.findByTitle("Less than 1 year") == null) {
      experienceRepository.save(new Experience("Less than 1 year", "Less than 1 year"));
    }

    if (experienceRepository.findByTitle("1 to 3 years") == null) {
      experienceRepository.save(new Experience("1 to 3 years", "1 to 3 years"));
    }

    if (experienceRepository.findByTitle("3 to 5 years") == null) {
      experienceRepository.save(new Experience("3 to 5 years", "3 to 5 years"));
    }

    if (experienceRepository.findByTitle("More than 5 years") == null) {
      experienceRepository.save(new Experience("More than 5 years", "More than 5 years"));
    }

    // dump data table recruitment type

    if (recruitmentTypeRepository.findByTitle("New") == null) {
      recruitmentTypeRepository.save(new RecruitmentType("New", "New"));
    }

    if (recruitmentTypeRepository.findByTitle("Instead") == null) {
      recruitmentTypeRepository.save(new RecruitmentType("Instead", "Instead"));
    }

    // dump data table foreign

    if (foreignLanguageRepository.findByTitle("English") == null) {
      foreignLanguageRepository.save(new ForeignLanguage("English", "English"));
    }

    if (foreignLanguageRepository.findByTitle("Japanese") == null) {
      foreignLanguageRepository.save(new ForeignLanguage("Japanese", "Japanese"));
    }

    if (foreignLanguageRepository.findByTitle("Korean") == null) {
      foreignLanguageRepository.save(new ForeignLanguage("Korean", "Korean"));
    }

  }
}
