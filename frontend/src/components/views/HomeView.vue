<script setup>

import FooterComp from "@/components/FooterComp.vue";
import HeaderComp from "@/components/HeaderComp.vue";
import Utils from "@/scripts/Utils.js"

import {onMounted} from "vue";

onMounted(() => {
  Utils.doInfoAction()
})
</script>

<template>
  <HeaderComp/>

  <div class="w-50 main-body">
    <div class="m-b-3em">
      <h1 class="main-body-header">
        GoCow: Разделяй работу и дом!
      </h1>
      <p>Наше средство поможет Вам более эффективно управлять рабочим пространством в любой сфере:</p>
      <ul>
        <li>Коворкинг</li>
        <li>Аренда помещений</li>
        <li>Занятие переговорных комнат в офисе</li>
      </ul>
    </div>
    <div class="m-b-3em">
      <h1 class="main-body-header">
        Отслеживайте занятость помещений
      </h1>
      <p>
        Вы можете наблюдать за тем, какие помещения заняты/арендованы, отслеживать, когда
        появится свободное место, а также когда должна быть произведена оплата на продление.
      </p>
      <p>
        Помимо всего прочего, ваши клиенты или сотрудники также могут арендовать/занимать помещения
        для своих нужд от занятия переговорной комнаты до аренды целого помещения на долгий срок!
      </p>
      <img th:src="@{image/main-office-plan.jpg}"/>
    </div>
    <div class="m-b-3em">
      <h1 class="main-body-header">
        Быстро, надежно
      </h1>
      <p>
        Оплата за пользование помещений происходит быстро и надежно. Наш сервис использует
        готовые надежные решения по оплате, что обеспечивает безопасность платежных данных клиента.
      </p>
    </div>
    <div class="m-b-3em">
      <h1 class="main-body-header">
        Низкие комиссии
      </h1>
      <p>
        Мы берем всего 1% от ваших сделок! Либо 30 рублей в месяц в случае, если ваше пространство - это офис
      </p>
    </div>
  </div>

  <FooterComp/>
</template>

<style scoped>

</style>