package com.example.templateapplication.data

import com.example.templateapplication.models.Doctor

object DoctorSampler {
    val doctorList = mutableListOf(
        Doctor(1, "Dr. Smith", "Male", "Cardiologist", "Expert in heart-related issues"
        , "fjdsfsdf", "fdjksf","hdjkffdsf", "321"),
        Doctor(2, "Dr. Johnson", "Female", "Orthopedic Surgeon", "Specialized in bone and joint surgeries" , "fjdsfsdf", "fdjksf","hdjkffdsf", "321" ),
        Doctor(3, "Dr. Williams", "Male", "Dermatologist", "Skilled in treating skin conditions" , "fjdsfsdf", "fdjksf","hdjkffdsf", "321"),
        Doctor(4, "Dr. Davis", "Female", "Pediatrician", "Focused on child healthcare" , "fjdsfsdf", "fdjksf","hdjkffdsf", "321"),
        Doctor(5, "Dr. Anderson", "Male", "Ophthalmologist", "Deals with eye-related problems" , "fjdsfsdf", "fdjksf","hdjkffdsf", "321")
    )

    val getAll: () -> MutableList<Doctor> = {
        val list = mutableListOf<Doctor>()
        for (item in doctorList) {
            list.add(item)
        }
        list
    }
}