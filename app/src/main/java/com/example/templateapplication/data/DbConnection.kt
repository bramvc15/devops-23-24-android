package com.example.templateapplication.data

import com.example.templateapplication.models.Doctor
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
fun main(){

    val serverName = "localhost"  // Replace with your SQL Server hostname or IP address
    val portNumber = "1433"           // The default port for SQL Server is 1433, but it may vary
    val databaseName = "CMS_content"
    val jdbcUrl = "jdbc:sqlserver://$serverName:$portNumber;databaseName=$databaseName"

    val username = "root" // Replace with your SQL Server username
    val password = "root" // Replace with your SQL Server password


    val connection: Connection = DriverManager.getConnection(jdbcUrl, username, password)
        // Now you have a database connection (connection) to work with


    println(connection.isValid(0))

    // the query is only prepared not executed
    val query = connection.prepareStatement("SELECT * FROM users")

    // the query is executed and results are fetched
    val result = query.executeQuery()

    // an empty list for holding the results
    val users = mutableListOf<Doctor>()

    while(result.next()){

        // getting the value of the id column
        val id = result.getInt("id")

        // getting the value of the name column
        val name = result.getString("name")

        val gender = result.getString("gender")
        val specialization = result.getString("specialization")
        val infoText = result.getString("infoText")

        /*
        constructing a User object and
        putting data into the list
         */
        users.add(Doctor(id, name, gender, specialization, infoText))
    }
    /*
    [User(id=1, name=Kohli), User(id=2, name=Rohit),
    User(id=3, name=Bumrah), User(id=4, name=Dhawan)]
     */
    println(users)


}

suspend fun fetchDataFromDatabase(): List<Doctor> {
    return withContext(Dispatchers.IO) {
        val serverName = "localhost"
        val portNumber = "1433"
        val databaseName = "CMS_content"
        //val jdbcUrl = "jdbc:sqlserver://$serverName:$portNumber;databaseName=$databaseName"
        val jdbcUrl = "jdbc:sqlserver://localhost;encrypt=true;integratedSecurity=true;"

        val username = ""
        val password = ""

        val doctors = mutableListOf<Doctor>()

        try {
            val connection: Connection = DriverManager.getConnection(jdbcUrl, username, password)
            val query: PreparedStatement = connection.prepareStatement("SELECT * FROM doctors")
            val result: ResultSet = query.executeQuery()
            println(result)
            while (result.next()) {
                val id = result.getInt("id")
                val name = result.getString("name")
                val gender = result.getString("gender")
                val specialization = result.getString("specialization")
                val infoText = result.getString("infoText")

                doctors.add(Doctor(id, name, gender, specialization, infoText))
            }

            connection.close()
        } catch (e: SQLException) {
            // Handle any database connection or query errors
            e.printStackTrace()
        }

        doctors
    }
}