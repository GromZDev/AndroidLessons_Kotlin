package com.example.kotlin_lesson_1.view.maps

import android.Manifest
import android.content.pm.PackageManager
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.kotlin_lesson_1.R
import com.example.kotlin_lesson_1.databinding.FragmentMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

const val REQUEST_CODE_FOR_MAPS = 723

class MapsFragment : Fragment() {

    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!

    //     callback, он вызовется, когда карта будет готова к отображению и с ней можно будет работать
    private val callback = OnMapReadyCallback { googleMap ->

        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
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

        checkGPSPermission()

    }

    private fun checkGPSPermission() {
        context?.let {
            when {
                ContextCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED -> {

                    getMapData() // Если уже получено разрешение, то получаем контакты далее
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
}
