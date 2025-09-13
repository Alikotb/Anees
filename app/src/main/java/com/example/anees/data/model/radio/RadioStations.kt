package com.example.anees.data.model.radio

import com.example.anees.R
import com.example.anees.data.model.audio.AudioTrack

object RadioStations {
    val stations = listOf(
        RadioStation(
            "محمود خليل الحصري",
            "https://backup.qurango.net/radio/mahmoud_khalil_alhussary",
            " قارئ قرآن مصري ويعد أحد أعلام هذا المجال البارزين",
            R.drawable.hosary
        ),
        RadioStation(
            "إدريس أبكر",
            "https://backup.qurango.net/radio/idrees_abkr",
            "قارئ سعودي من مدينة جدة، اشتهر بصوته المؤثر في تلاوة القرآن الكريم",
            R.drawable.idris
        ),
        RadioStation(
            "سعد الغامدي",
            "https://backup.qurango.net/radio/saad_alghamdi",
            "إمام وخطيب سعودي، له تسجيلات شهيرة بتلاواته العذبة والمؤثرة",
            R.drawable.saad
        ),
        RadioStation(
            "عبد الباسط عبد الصمد",
            "https://backup.qurango.net/radio/abdulbasit_abdulsamad_mojawwad",
            "من أشهر قراء العالم الإسلامي، تميز بصوت قوي وأداء متقن",
            R.drawable.abdelbaset
        ),
        RadioStation(
            "محمد صديق المنشاوي",
            "https://backup.qurango.net/radio/mohammed_siddiq_alminshawi_mojawwad",
            "أحد أشهر قراء مصر والعالم الإسلامي، تميز بأسلوب شجي وخاشع",
            R.drawable.menshawy
        ),
        RadioStation(
            "محمد اللحيدان",
            "https://backup.qurango.net/radio/mohammed_allohaidan",
            "قارئ سعودي يتميز بصوت خاشع وأداء مميز في تلاوة القرآن",
            R.drawable.layhadan
        ),
        RadioStation(
            "ماهر المعيقلي",
            "https://backup.qurango.net/radio/maher",
            "إمام وخطيب المسجد الحرام بمكة المكرمة، معروف بصوته الهادئ المؤثر",
            R.drawable.maher
        ),
        RadioStation(
            "ياسر الدوسري",
            "https://backup.qurango.net/radio/yasser_aldosari",
            "إمام وخطيب المسجد الحرام، يتمتع بصوت مؤثر وأداء متميز",
            R.drawable.dosri
        ),
        RadioStation(
            "ناصر القطامي",
            "https://backup.qurango.net/radio/nasser_alqatami",
            "إمام سعودي معروف بصوته الجميل وأسلوبه الهادئ",
            R.drawable.qtamy
        ),
/*        RadioStation(
            "عبد الرحمن السديس",
            "https://backup.qurango.net/radio/abdulrahman_alsudaes",
            "إمام وخطيب المسجد الحرام، من أشهر قراء العالم الإسلامي",
            R.drawable.sudis
        ),*/
        RadioStation(
            "أحمد العجمي",
            "https://backup.qurango.net/radio/ahmad_alajmy",
            "قارئ سعودي متميز بصوته العذب وأسلوبه المؤثر",
            R.drawable.agamy
        ),
        RadioStation(
            "حاتم فريد الواعر",
            "https://backup.qurango.net/radio/hatem_fareed_alwaer",
            "قارئ مصري عرف بصوته الهادئ والخاشع",
            R.drawable.hatem
        ),
        RadioStation(
            "سعود الشريم",
            "https://backup.qurango.net/radio/saud_alshuraim",
            "إمام المسجد الحرام بمكة المكرمة، له تسجيلات مؤثرة",
            R.drawable.sherim
        ),
        RadioStation(
            "خالد الجليل",
            "https://backup.qurango.net/radio/khalid_aljileel",
            "إمام جامع الملك خالد بالرياض، معروف بتلاوته الخاشعة",
            R.drawable.galel
        ),
        RadioStation(
            "محمد الطبلاوي",
            "https://backup.qurango.net/radio/mohammad_altablaway",
            "من كبار قراء مصر، اشتهر بأسلوبه المؤثر وصوته العذب",
            R.drawable.tablawy
        ),
        RadioStation(
            "محمد أيوب",
            "https://backup.qurango.net/radio/mohammed_ayyub",
            "إمام المسجد النبوي سابقًا، له تلاوات مميزة بصوت هادئ",
            R.drawable.ayub
        ),
        RadioStation(
            "محمد جبريل",
            "https://backup.qurango.net/radio/mohammed_jibreel",
            "قارئ مصري معروف بجمال صوته وخشوع تلاوته",
            R.drawable.gebril
        ),
        RadioStation(
            "أبو بكر الشاطري",
            "https://backup.qurango.net/radio/shaik_abu_bakr_al_shatri",
            "تلاوات عذبة بصوت الشيخ أبو بكر الشاطري، يتميز بجمال الأداء وجودة الترتيل.",
            R.drawable.shatri
        ),
        RadioStation(
            "محمود علي البنا",
            "https://backup.qurango.net/radio/mahmoud_ali__albanna_mojawwad",
            "قارئ مصري متميز بصوته الرخيم وأدائه المؤثر.",
            R.drawable.elbana
        ),
        RadioStation(
            "مصطفى اللاهوني",
            "https://backup.qurango.net/radio/mustafa_allahoni",
            "قارئ مصري له تلاوات جميلة بصوت مؤثر",
            R.drawable.mostafa
        ),
        RadioStation(
            "مصطفى رعد العزاوي",
            "https://backup.qurango.net/radio/mustafa_raad_alazawy",
            "قارئ عراقي، له تلاوات بأسلوب مميز وخاشع",
            R.drawable.ezawy
        ),
        RadioStation(
            "جمعان العصيمي",
            "https://backup.qurango.net/radio/jamaan_alosaimi",
            "قارئ سعودي يتميز بأداء مؤثر وخاشع",
            R.drawable.osaimy
        ),
        RadioStation(
            "معيض الحارثي",
            "https://backup.qurango.net/radio/moeedh_alharthi",
            "قارئ سعودي، له تلاوات متميزة",
            R.drawable.harthy
        ),
        RadioStation(
            "أحمد الطرابلسي",
            "https://backup.qurango.net/radio/ahmed_altrabulsi",
            "قارئ كويتي، يتميز بصوت مؤثر وأداء مميز",
            R.drawable.trablsy
        ),
        RadioStation(
            "إذاعة أنيس",
            "https://backup.qurango.net/radio/mix",
            "باقة مختارة من أجمل وأعذب تلاوات القراء",
            R.drawable.logo_foreground
        ),
        RadioStation(
            "تلاوات خاشعة",
            "https://backup.qurango.net/radio/salma",
            "باقة مختارة من أجمل وأعذب تلاوات القراء",
            R.drawable.telawaat
        ),
        RadioStation(
            "الفتاوى",
            "https://backup.qurango.net/radio/fatwa",
            "إذاعة مختصة ببث الفتاوى الدينية المباشرة",
            R.drawable.fatwa
        ),
        RadioStation(
            "الرُقية الشرعية",
            "https://backup.qurango.net/radio/roqiah",
            "إذاعة متخصصة في الرُقية الشرعية وآيات الشفاء",
            R.drawable.roqia
        ),
        RadioStation(
            "تفسير القرآن",
            "https://backup.qurango.net/radio/tafseer",
            "بث مباشر لتفسير آيات القرآن الكريم من كبار العلماء",
            R.drawable.tafsirquran
        ),
        RadioStation(
            "إذاعة رمضان",
            "https://backup.qurango.net/radio/ramadan",
            "إذاعة خاصة بشهر رمضان الكريم بتلاوات وابتهالات رمضانية",
            R.drawable.ramadan
        ),
        RadioStation(
            "ستة أيام من شوال",
            "https://backup.qurango.net/radio/SixDaysOfShawwal",
            "إذاعة مخصصة لتلاوات وأحاديث عن صيام الست من شوال",
            R.drawable.shawaal
        ),
        RadioStation(
            "يوم عاشوراء",
            "https://backup.qurango.net/radio/TheDayofAshoora",
            "بث مباشر لتلاوات وأحاديث عن فضل يوم عاشوراء",
            R.drawable.ashoraa
        ),
        RadioStation(
            "سيرة النبي محمد ﷺ",
            "https://backup.qurango.net/radio/fi_zilal_alsiyra",
            "إذاعة مختصة ببث سيرة النبي محمد ﷺ بشكل مفصل",
            R.drawable.sera
        ),
        RadioStation(
            "مختصر السيرة النبوية",
            "https://backup.qurango.net/radio/almukhtasar_fi_alsiyra",
            "بث مباشر للسيرة النبوية المختصرة وأحداثها المهمة",
            R.drawable.sera
        ),
        RadioStation(
            "عشر ذي الحجة",
            "https://backup.qurango.net/radio/ten_dhul_hijjah",
            "إذاعة مخصصة لتلاوات وأحاديث عن فضل أيام العشر",
            R.drawable.ashr
        ),
        RadioStation(
            "تفسير الطبري - ختمة",
            "https://backup.qurango.net/radio/tabri",
            "بث مباشر لختم تفسير القرآن الكريم من كتاب الطبري",
            R.drawable.telawaat
        )
    )
}

val audioStations = RadioStations.stations.mapIndexed { index, station ->
    AudioTrack(
        index = index,
        title = station.name,
        description = station.description,
        reciter = station.name,
        reciterImage = station.imageResId,
        typeIcon = "",
        reciterBaseUrl = "",
        uri = station.url,
    )
 }
