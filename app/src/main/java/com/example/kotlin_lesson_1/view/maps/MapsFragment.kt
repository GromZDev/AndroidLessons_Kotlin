package com.example.kotlin_lesson_1.view.maps

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.kotlin_lesson_1.R
import com.example.kotlin_lesson_1.databinding.FragmentMapsBinding
import com.example.kotlin_lesson_1.model.credits.Cast
import com.example.kotlin_lesson_1.model.gettingPersonIdForPerson.PersonForId
import com.example.kotlin_lesson_1.model.person.Person
import com.example.kotlin_lesson_1.repository.creditForPersonRepository.CreditForPersonRepository
import com.example.kotlin_lesson_1.repository.person.PersonRepository
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_maps.*
import java.io.IOException

const val REQUEST_CODE_FOR_MAPS = 723

class MapsFragment : Fragment() {

    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!

    private lateinit var actorsIdBundle: Cast

    private lateinit var creditID: String
    private var personID: Int = 0
    private lateinit var personPlaceOfBirth: String
    private lateinit var thisMap: GoogleMap

    //     callback, он вызовется, когда карта будет готова к отображению и с ней можно будет работать
    private val callback = OnMapReadyCallback { googleMap ->
        thisMap = googleMap
        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        activateMyLocation(thisMap) // Сетим появление штатной кнопки для показа моего места

    }

    companion object {
        const val BUNDLE_MAP_EXTRA = "MY_Maps"

        // Передаем во фрагмент бандл с данными фильма
        fun newInstance(bundle: Bundle): MapsFragment {
            val fragment = MapsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        actorsIdBundle = arguments?.getParcelable(BUNDLE_MAP_EXTRA) ?: Cast(
            0, 0, "1", "1", 0, "1", "1"
        )

        creditID = actorsIdBundle.credit_id // Берем переданные данные конкретного актёра

        checkGPSPermission() // Запрашиваем все разрешения
    }

    private fun checkGPSPermission() {
        context?.let {
            when {
                ContextCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED -> {

                    getMapData() // Если уже получено разрешение, то получаем контакты далее

                    getPersonID(creditID) // Для получения ID актера передаём сюда полученный credit_id

                    initSearchByAddress() // Это логика поиска при вводе места вручную
                }
                // Метод для нас, чтобы знали когда необходимы пояснения показывать перед запросом:
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                    AlertDialog.Builder(it)
                        .setTitle("Необходим доступ к GPS")
                        .setMessage(
                            "Внимание! Для просмотра данных на карте необходимо разрешение на" +
                                    "использование Вашего местоположения"
                        )
                        .setPositiveButton("Предоставить доступ") { _, _ ->
                            requestGPSPermission()
                        }
                        .setNegativeButton("Спасибо, не надо") { dialog, _ -> dialog.dismiss() }
                        .create()
                        .show()
                }
                else -> {

                    requestGPSPermission() // Если разрешения нет и объяснение отображать не нужно, то
                    // запрашиваем разрешение
                }
            }
        }
    }

    private fun getMapData() {

        // находим нужный нам фрагмент, приводим его к типу SupportMapFragment и подготавливаем карту
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun requestGPSPermission() {
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE_FOR_MAPS)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE_FOR_MAPS -> {
                // Проверяем, дано ли пользователем разрешение по нашему запросу
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                    getMapData()
                } else {
                    // Поясните пользователю, что экран останется пустым, потому что доступ к контактам не предоставлен
                    context?.let {
                        AlertDialog.Builder(it)
                            .setTitle("Доступ к GPS")
                            .setMessage(
                                "Внимание! Для просмотра данных на карте необходимо разрешение на " +
                                        "использование Вашего местоположения"
                            )
                            .setNegativeButton("Закрыть") { dialog, _ -> dialog.dismiss() }
                            .create()
                            .show()
                    }
                }
                return
            }
        }
    }

    private fun initActorBirthPlace(place: String) {
        val geoCoder = Geocoder(view?.context)
        Thread {
            try {
                val addresses = geoCoder.getFromLocationName(place, 1)
                if (addresses.size > 0) {
                    view?.let { goToAddress(addresses, it, place) }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()
    }

    private fun initSearchByAddress() {
        binding.mapButtonSearch.setOnClickListener {
            val geoCoder = Geocoder(it.context)
            val place = map_searchAddress.text.toString()
            Thread {
                try {
                    val addresses = geoCoder.getFromLocationName(place, 1)
                    if (addresses.size > 0) {
                        goToAddress(addresses, it, place)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }.start()
        }
    }

    // Логика получения ID актера (onCreditIdFetched) для последующей передачи в функцию получения
    // полных api данных по конкретному актёру
    private fun getPersonID(creditID: String) {
        CreditForPersonRepository.getPersonId(
            creditID,
            ::onCreditIdFetched,
            ::onError
        )
    }

    private fun goToAddress(
        addresses: MutableList<Address>,
        view: View,
        searchText: String
    ) {
        val location = LatLng(
            addresses[0].latitude,
            addresses[0].longitude
        )
        view.post {
            setMarker(location, searchText)
            thisMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    location,
                    10f
                )
            )
        }
    }

    private fun setMarker(
        location: LatLng,
        searchText: String
    ): Marker {
        return thisMap.addMarker(
            MarkerOptions()
                .position(location)
                .title(searchText)
                .icon(BitmapDescriptorFactory.defaultMarker())
        )
    }

    private fun onCreditIdFetched(actors: PersonForId) {
        personID = actors.id // тянем id для передачи в функцию получения полных данных по актеру
        getPersonDataBirth(personID)
    }

    private fun getPersonDataBirth(personID: Int) {
        PersonRepository.getPersonData(
            "ru",
            personID,
            ::onPersonDataFetched,
            ::onError
        )
    }

    private fun onError() {
        Toast.makeText(context, "ERROR<<<<<<<<<<<<<<<<<<<<", Toast.LENGTH_SHORT).show()
    }

    private fun onPersonDataFetched(person: Person) {
        personPlaceOfBirth = person.place_of_birth.toString()
        Toast.makeText(
            context,
            getString(R.string.output_birth_place) + " $personPlaceOfBirth",
            Toast.LENGTH_LONG
        ).show()
        initActorBirthPlace(personPlaceOfBirth) // Полученное место актера передаем в карту
    }

    private fun activateMyLocation(googleMap: GoogleMap) {
        context?.let {
            val isPermissionGranted =
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) ==
                        PackageManager.PERMISSION_GRANTED
            googleMap.isMyLocationEnabled = isPermissionGranted
            googleMap.uiSettings.isMyLocationButtonEnabled = isPermissionGranted
        }
    }
}
