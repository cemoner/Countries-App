package com.example.kotlincountries.services

import com.example.kotlincountries.model.entities.Country
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.tasks.await

class FirestoreCountryOperations {
    private val db = FirebaseFirestore.getInstance()
    private val countriesCollection = db.collection("countries")

    // Add a new country
    suspend fun addCountry(country: Country, onSuccess: () -> Unit, onFailure: (Exception) -> Unit): Result<Any> {
        // Create a map of the country data (excluding Room's uuid as Firestore will generate its own ID)
        val countryMap = hashMapOf(
            "name" to country.countryName,
            "capital" to country.countryCapital,
            "region" to country.countryRegion,
            "currency" to country.countryCurrency,
            "flag" to country.imageUrl,
            "language" to country.countryLanguage
        )
// Convert name to lowercase for consistent checking
        val normalizedName = country.countryName

        // Query to check if country exists
        val snapshot = db.collection("countries")
            .whereEqualTo("name", normalizedName)
            .get()
            .await()

        if (!snapshot.isEmpty) {
            // Country already exists
            onFailure(Exception("Country already exists"))
            return Result.failure(Exception("Country already exists"))
        } else {
            // Country doesn't exist, add it
            countriesCollection
                .add(countryMap)
                .addOnSuccessListener { documentReference ->
                    onSuccess()
                }
                .addOnFailureListener { e ->
                    onFailure(e)
                }
            return Result.success({
            })
        }
    }

    // Get a single country by name
    fun getCountryByName(name: String, onSuccess: (Country?) -> Unit, onFailure: (Exception) -> Unit) {
        countriesCollection
            .whereEqualTo("name", name)
            .get()
            .addOnSuccessListener { documents ->
                val country = documents.firstOrNull()?.let { doc ->
                    Country(
                        countryName = doc.getString("name"),
                        countryCapital = doc.getString("capital"),
                        countryRegion = doc.getString("region"),
                        countryCurrency = doc.getString("currency"),
                        imageUrl = doc.getString("flag"),
                        countryLanguage = doc.getString("language")
                    )
                }
                onSuccess(country)
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
    }

    // Get all countries
    fun getAllCountries(onSuccess: (List<Country>) -> Unit, onFailure: (Exception) -> Unit) {
        countriesCollection
            .get()
            .addOnSuccessListener { result ->
                val countries = result.map { doc ->
                    Country(
                        countryName = doc.getString("name"),
                        countryCapital = doc.getString("capital"),
                        countryRegion = doc.getString("region"),
                        countryCurrency = doc.getString("currency"),
                        imageUrl = doc.getString("flag"),
                        countryLanguage = doc.getString("language")
                    )
                }
                onSuccess(countries)
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
    }

    // Get countries by region
    fun getCountriesByRegion(region: String, onSuccess: (List<Country>) -> Unit, onFailure: (Exception) -> Unit) {
        countriesCollection
            .whereEqualTo("region", region)
            .get()
            .addOnSuccessListener { result ->
                val countries = result.map { doc ->
                    Country(
                        countryName = doc.getString("name"),
                        countryCapital = doc.getString("capital"),
                        countryRegion = doc.getString("region"),
                        countryCurrency = doc.getString("currency"),
                        imageUrl = doc.getString("flag"),
                        countryLanguage = doc.getString("language")
                    )
                }
                onSuccess(countries)
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
    }

    // Real-time updates for all countries
    fun listenToCountries(onUpdate: (List<Country>) -> Unit, onError: (Exception) -> Unit): ListenerRegistration {
        return countriesCollection
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    onError(e)
                    return@addSnapshotListener
                }

                val countries = snapshot?.documents?.map { doc ->
                    Country(
                        countryName = doc.getString("name"),
                        countryCapital = doc.getString("capital"),
                        countryRegion = doc.getString("region"),
                        countryCurrency = doc.getString("currency"),
                        imageUrl = doc.getString("flag"),
                        countryLanguage = doc.getString("language")
                    )
                } ?: emptyList()
                onUpdate(countries)
            }
    }

    // Update a country
    fun updateCountry(oldName:String,newName: String, updates: Map<String, Any>, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        countriesCollection
            .whereEqualTo("name", oldName)
            .get()
            .addOnSuccessListener { documents ->
                val document = documents.firstOrNull()
                if (document != null) {
                    val updatedFields = updates.toMutableMap()
                    updatedFields["name"] = newName
                    document.reference.update(updatedFields)
                        .addOnSuccessListener { onSuccess() }
                        .addOnFailureListener { e -> onFailure(e) }
                } else {
                    onFailure(Exception("Country not found"))
                }
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
    }

    // Delete a country
    fun deleteCountry(countryName: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        countriesCollection
            .whereEqualTo("name", countryName)
            .get()
            .addOnSuccessListener { documents ->
                val document = documents.firstOrNull()
                if (document != null) {
                    document.reference.delete()
                        .addOnSuccessListener { onSuccess() }
                        .addOnFailureListener { e -> onFailure(e) }
                } else {
                    onFailure(Exception("Country not found"))
                }
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
    }
}