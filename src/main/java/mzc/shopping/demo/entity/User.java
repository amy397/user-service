package mzc.shopping.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name ="users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(nullable = false, unique=true, length =50)
   private String email;

   @Column(nullable = false)
   private String password;

   @Column(nullable = false,length=30)
   private String name;

   @Column(length = 20)
   private String phone;


   @Enumerated(EnumType.STRING)
   @Column(nullable = false)
   @Builder.Default
   private Role role =Role.USER;

   @CreationTimestamp
   private LocalDateTime createdAt;

   @UpdateTimestamp
   private LocalDateTime updatedAt;

   public enum Role {
       USER,
       ADMIN
   }








}
