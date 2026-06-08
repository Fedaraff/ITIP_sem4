package org.example.repository;

import org.example.model.entity.Notification;
import org.example.model.enums.NotificationChannel;
import org.example.model.enums.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByStatus(NotificationStatus status);

    List<Notification> findByChannel(NotificationChannel channel);

    List<Notification> findByRecipientId(Long recipientId);


    @Query("SELECT n FROM Notification n WHERE n.status = :status AND n.channel = :channel")
    List<Notification> findByStatusAndChannel(@Param("status") NotificationStatus status,
                                              @Param("channel") NotificationChannel channel);

    @Query("SELECT n FROM Notification n ORDER BY n.createdAt DESC")
    List<Notification> findAllOrderByCreatedAtDesc();

    @Query("SELECT n FROM Notification n WHERE n.recipient.id = :recipientId AND n.status = :status")
    List<Notification> findByRecipientIdAndStatus(@Param("recipientId") Long recipientId,
                                                  @Param("status") NotificationStatus status);
}