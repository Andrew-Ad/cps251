package com.example.contacts

import kotlinx.coroutines.flow.Flow


class ContactRepository(private val contactDao: ContactDao) {
    val allContacts: Flow<List<Contact>> = contactDao.getAllContacts()

    suspend fun insert(contact: Contact) = contactDao.insert(contact)

    suspend fun delete(contact: Contact) = contactDao.delete(contact)
}


