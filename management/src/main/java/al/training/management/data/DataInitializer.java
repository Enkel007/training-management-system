package al.training.management.data;

import al.training.management.model.Role;
import al.training.management.model.User;
import al.training.management.repository.RoleRepository;
import al.training.management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Transactional
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Set<String> defaultRoles =  Set.of("ROLE_ADMIN", "ROLE_STUDENT", "ROLE_TEACHER");
        createDefaultRoleIfNotExits(defaultRoles);
        createDefaultStudentIfNotExits();
        createDefaultTeacherIfNotExits();
        createDefaultAdminIfNotExits();
    }

    private void createDefaultStudentIfNotExits(){
        Optional<Role> userRoleOptional = roleRepository.findByName("ROLE_STUDENT");
        if (userRoleOptional.isEmpty()) {
            System.out.println("ROLE_STUDENT not found");
            return;
        }
        Role userRole = userRoleOptional.get();
        for (int i = 1; i<=5; i++){
            String defaultEmail = "user"+i+"@email.com";
            if (userRepository.existsByEmail(defaultEmail)){
                continue;
            }
            User user = new User();
            user.setFirstName("User");
            user.setLastName("McUser" + i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Set.of(userRole));
            userRepository.save(user);
            System.out.println("Default vet user " + i + " created successfully.");
        }
    }

    private void createDefaultTeacherIfNotExits(){
        Optional<Role> teacherRoleOptional = roleRepository.findByName("ROLE_TEACHER");
        if (teacherRoleOptional.isEmpty()) {
            System.out.println("ROLE_TEACHER not found");
            return;
        }
        Role teacherRole = teacherRoleOptional.get();
        for (int i = 1; i<=2; i++){
            String defaultEmail = "teacher"+i+"@email.com";
            if (userRepository.existsByEmail(defaultEmail)){
                continue;
            }
            User user = new User();
            user.setFirstName("Teacher");
            user.setLastName("Teacherllari" + i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Set.of(teacherRole));
            userRepository.save(user);
            System.out.println("Default teacher user " + i + " created successfully.");
        }
    }

    private void createDefaultAdminIfNotExits(){
        Optional<Role> adminRoleOptional = roleRepository.findByName("ROLE_ADMIN");
        if (adminRoleOptional.isEmpty()) {
            System.out.println("ROLE_ADMIN not found");
            return;
        }
        Role adminRole = adminRoleOptional.get();
        for (int i = 1; i<=2; i++){
            String defaultEmail = "admin"+i+"@email.com";
            if (userRepository.existsByEmail(defaultEmail)){
                continue;
            }
            User user = new User();
            user.setFirstName("Admin");
            user.setLastName("Admini" + i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Set.of(adminRole));
            userRepository.save(user);
            System.out.println("Default admin user " + i + " created successfully.");
        }
    }

    private void createDefaultRoleIfNotExits(Set<String> roles){
        roles.stream()
                .filter(role -> roleRepository.findByName(role).isEmpty())
                .map(Role:: new).forEach(roleRepository::save);
    }
}