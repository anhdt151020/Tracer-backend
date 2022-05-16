package com.tracer.app.service;

import com.tracer.app.domain.TransactionHistory;
import com.tracer.app.repository.TransactionHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TransactionHistory}.
 */
@Service
@Transactional
public class TransactionHistoryService {

    private final Logger log = LoggerFactory.getLogger(TransactionHistoryService.class);

    private final TransactionHistoryRepository transactionHistoryRepository;

    public TransactionHistoryService(TransactionHistoryRepository transactionHistoryRepository) {
        this.transactionHistoryRepository = transactionHistoryRepository;
    }

    /**
     * Save a transactionHistory.
     *
     * @param transactionHistory the entity to save.
     * @return the persisted entity.
     */
    public TransactionHistory save(TransactionHistory transactionHistory) {
        log.debug("Request to save TransactionHistory : {}", transactionHistory);
        return transactionHistoryRepository.save(transactionHistory);
    }

    /**
     * Partially update a transactionHistory.
     *
     * @param transactionHistory the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TransactionHistory> partialUpdate(TransactionHistory transactionHistory) {
        log.debug("Request to partially update TransactionHistory : {}", transactionHistory);

        return transactionHistoryRepository
            .findById(transactionHistory.getId())
            .map(
                existingTransactionHistory -> {
                    if (transactionHistory.getTransactionState() != null) {
                        existingTransactionHistory.setTransactionState(transactionHistory.getTransactionState());
                    }
                    if (transactionHistory.getUserId() != null) {
                        existingTransactionHistory.setUserId(transactionHistory.getUserId());
                    }
                    if (transactionHistory.getTotalValue() != null) {
                        existingTransactionHistory.setTotalValue(transactionHistory.getTotalValue());
                    }
                    if (transactionHistory.getErrorMsq() != null) {
                        existingTransactionHistory.setErrorMsq(transactionHistory.getErrorMsq());
                    }
                    if (transactionHistory.getCreated() != null) {
                        existingTransactionHistory.setCreated(transactionHistory.getCreated());
                    }
                    if (transactionHistory.getCreatedBy() != null) {
                        existingTransactionHistory.setCreatedBy(transactionHistory.getCreatedBy());
                    }

                    return existingTransactionHistory;
                }
            )
            .map(transactionHistoryRepository::save);
    }

    /**
     * Get all the transactionHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TransactionHistory> findAll(Pageable pageable) {
        log.debug("Request to get all TransactionHistories");
        return transactionHistoryRepository.findAll(pageable);
    }

    /**
     * Get one transactionHistory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TransactionHistory> findOne(Long id) {
        log.debug("Request to get TransactionHistory : {}", id);
        return transactionHistoryRepository.findById(id);
    }

    /**
     * Delete the transactionHistory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TransactionHistory : {}", id);
        transactionHistoryRepository.deleteById(id);
    }
}
