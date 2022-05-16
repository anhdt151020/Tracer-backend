package com.tracer.app.repository;

import com.tracer.app.domain.SmsPhone;
import com.tracer.app.dto.NameAndNumberDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data SQL repository for the SmsPhone entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SmsPhoneRepository extends JpaRepository<SmsPhone, Long> , JpaSpecificationExecutor<SmsPhone> {
    List<SmsPhone> findAllByDeviceId(Long deviceId);

    Page<SmsPhone> findAllByDeviceIdAndAndNumber(Long deviceId, String number, Pageable pageable);

    Optional<SmsPhone> findByDeviceIdAndSmsId(Long deviceId, Long smsId);

    Page<SmsPhone> findByDeviceId(Long id, Pageable pageable);

    @Query("select new com.tracer.app.dto.NameAndNumberDTO(smsPhone.name,smsPhone.number) " +
        "from SmsPhone smsPhone where smsPhone.device.id =:id " +
        "and (smsPhone.number like :search or smsPhone.name like :search) " +
        "group by smsPhone.number, smsPhone.name " +
        "order by max(smsPhone.date) DESC ")
    Page<NameAndNumberDTO> getNameAndNumberByDeviceInfoIdAndSearch(@Param("id") Long id,@Param("search") String search, Pageable pageable);

    @Query("select new com.tracer.app.dto.NameAndNumberDTO(smsPhone.name,smsPhone.number) " +
        "from SmsPhone smsPhone where smsPhone.device.id =:id " +
        "and smsPhone.data like :search " +
        "group by smsPhone.number, smsPhone.name " +
        "order by max(smsPhone.date) DESC ")
    Page<NameAndNumberDTO> getNameAndNumberByDeviceInfoIdAndData(@Param("id") Long id,@Param("search") String search, Pageable pageable);

    @Query("select new com.tracer.app.dto.NameAndNumberDTO(smsPhone.name,smsPhone.number) " +
        "from SmsPhone smsPhone where smsPhone.device.id =:id " +
        "group by smsPhone.number, smsPhone.name " +
        "order by max(smsPhone.date) DESC ")
    Page<NameAndNumberDTO> getNameAndNumberByDeviceInfoId(@Param("id") Long id, Pageable pageable);

}
