package com.example.anees.enums

import com.example.anees.data.model.RecitationModel
import com.example.anees.utils.*

enum class Recitations(
    val recitationName: String,
    val list: List<RecitationModel> = emptyList()
) {
    HAFS_AN_ASIM("حفص عن عاصم"),
    WARSH_AN_NAFI("ورش عن نافع"),
    KHALAF_AN_HAMZA("خلف عن حمزة"),
    AL_BAZZI_AN_IBN_KATHIR("البزي عن ابن كثير"),
    QALUN_AN_NAFI("قالون عن نافع"),
    QUNBUL_AN_IBN_KATHIR("قنبل عن ابن كثير"),
    AL_SUSI_AN_ABU_AMR("السوسي عن أبي عمرو"),
    QALUN_ABU_NASHIT("قالون عن نافع من طريق أبي نشيط"),
    YAQOUB_ROWAIS_RUH("قراءة يعقوب الحضرمي بروايتي رويس وروح"),
    WARSH_ABU_BAKR("ورش عن نافع من طريق أبي بكر الأصبهاني"),
    AL_BAZZI_QUNBUL("البزي وقنبل عن ابن كثير"),
    AL_DURI_AN_AL_KISAI("الدوري عن الكسائي"),
    AL_DURI_AN_ABU_AMR("الدوري عن أبي عمرو"),
    SHUBA_AN_ASIM("شعبة عن عاصم"),
    ABN_ZAKOON("ابن ذكوان عن ابن عامر"),
    WARSH_AZRAQ("ورش عن نافع من طريق الأزرق"),
    MUSHAF_MUALIM("المصحف المعلم"),//المصحف المرتل
    MUSHAF_MUJAWAD("المصحف المجود");
}
