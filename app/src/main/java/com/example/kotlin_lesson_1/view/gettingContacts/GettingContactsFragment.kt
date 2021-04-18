package com.example.kotlin_lesson_1.view.gettingContacts

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.kotlin_lesson_1.databinding.FragmentReceiveContactsFromContentProviderBinding

const val REQUEST_CODE_FOR_CONTACTS = 578

class GettingContactsFragment : Fragment() {

    private var _binding: FragmentReceiveContactsFromContentProviderBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(): GettingContactsFragment {
            return GettingContactsFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            FragmentReceiveContactsFromContentProviderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkContactPermission()

    }

    private fun checkContactPermission() {
        context?.let {
            when {
                ContextCompat.checkSelfPermission(it, Manifest.permission.READ_CONTACTS) ==
                        PackageManager.PERMISSION_GRANTED -> {

                    getContacts() // Если уже получено разрешение, то получаем контакты далее
                }
                // Метод для нас, чтобы знали когда необходимы пояснения показывать перед запросом:
                shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) -> {
                    AlertDialog.Builder(it)
                        .setTitle("Необходим доступ к контактам")
                        .setMessage(
                            "Внимание! Для звонков из этого приложения необходимо получить " +
                                    "доступ к Вашим контактам"
                        )
                        .setPositiveButton("Предоставить доступ") { _, _ ->
                            requestPermission()
                        }
                        .setNegativeButton("Спасибо, не надо") { dialog, _ -> dialog.dismiss() }
                        .create()
                        .show()
                }
                else -> {

                    requestPermission() // Если разрешения нет и объяснение отображать не нужно, то
                    // запрашиваем разрешение
                }
            }
        }
    }

    // Запрашиваем разрешение, передаём также код, уникальный. Необходимо указать все пермишоны
    // которые запрашиваем
    private fun requestPermission() {
        requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), REQUEST_CODE_FOR_CONTACTS)
    }

    //Так как запрос на разрешения обрабатывает система, мы можем получать ответ пользователя
    // только в методе onRequestPermissionsResults. Это обратный вызов после получения
    // разрешений от пользователя. Тут нам приходит инфа, к какому permission нам дали доступ.
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE_FOR_CONTACTS -> {
                // Проверяем, дано ли пользователем разрешение по нашему запросу
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                    getContacts()
                } else {
                    // Поясните пользователю, что экран останется пустым, потому что доступ к контактам не предоставлен
                    context?.let {
                        AlertDialog.Builder(it)
                            .setTitle("Доступ к контактам")
                            .setMessage(
                                "Внимание! Для звонков из этого приложения необходимо получить" +
                                        " доступ к Вашим контактам"
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

    // Далее юзер дал нам достум, и мы вызываем getContacts(). Тут необходим Content Provider.
    private fun getContacts() {
        val context = context ?: return
        context.let {
            // Получаем ContentResolver у Content Provider’а. Через него мы поличим курсор, а уже
            // через курсор будем читать данные.
            val contentResolver: ContentResolver = it.contentResolver
            // Отправляем запрос на получение контактов и получаем ответ в виде Cursor'а. У контент
            // резолвера вызываем метод query()/ Тут надо указать:
            val cursorWithContacts: Cursor = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI, // // Откуда берём данные. URI - адрес, где
    // находятся наши данные.
                null, // тут указываем что нам надо (если только номер, или имя,
                // null - все берем)
                null, // выборка. Например нам нужны контакты, имя которых начин-ся с А...
                null, // Подстановки в запрос.
                ContactsContract.Contacts.DISPLAY_NAME + " ASC" // Как мы будем сортировать
    // наши элементы
            ) ?: return // если курсор равен налл, то return

    // В курсоре двигаемся к первому элементу и начинаем двигаться по всем остальным через цикл.
            cursorWithContacts.let { cursor ->
                for (i in 0..cursor.count) {
                    // Переходим на позицию в Cursor’е
                    if (cursor.moveToPosition(i)) {
                        // Берём из Cursor’а столбец с именем, достаем данные через getString
                        val name =
                            cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                        addView(it, name)
                    }
                }
            }
            cursorWithContacts.close()
        }
    }

    // Тут просто добавляем вьюху
    private fun addView(context: Context, textToShow: String) {
        binding.containerForContacts.addView(AppCompatTextView(context).apply {
            text = textToShow
            textSize = 24.0f
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}