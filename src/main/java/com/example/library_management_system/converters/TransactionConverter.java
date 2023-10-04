package com.example.library_management_system.converters;

import com.example.library_management_system.DTO.responseDTO.ResponseTransaction;
import com.example.library_management_system.model.Transaction;
public class TransactionConverter {

    public static ResponseTransaction fromTransactionToResponseTransaction(Transaction transaction){
        return ResponseTransaction.builder().
                transactionId(transaction.getId()).
                issueDate(transaction.getTransactionDate()).
                studentName(transaction.getLibraryCard().getStudent().getName()).
                bookTitle(transaction.getBook().getTitle()).
                authorName(transaction.getBook().getAuthor().getName()).
                build();
    }
}