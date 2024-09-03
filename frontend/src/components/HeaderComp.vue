<script setup>
import {onMounted, ref} from "vue";
import initFunc from "@/scripts/Neurolinks.js";
import Utils from "@/scripts/Utils.js";

const isAuthenticated = ref(Utils.getCookie('_at') !== '')
console.log(isAuthenticated.value)

onMounted(() => {
  initFunc.initAll()
})

function logout() {
  Utils.logout();
  isAuthenticated.value = !isAuthenticated.value
}
</script>

<template>
  <header id="large-header" class="large-header">
    <canvas id="demo-canvas"></canvas>
    <div class="center-header-block">
      <h1>GoCow</h1>
      <div>Программное средство для бла бла бла бла бла</div>
    </div>
    <div id="logo">
    <img src="@/assets/images/logo.png">
    </div>

    <div class="right-header-block" v-if="isAuthenticated">
      <a class="header-btn" href="/account">
        Личный кабинет
      </a>
      <a class="header-btn" href="/#" @click.prevent="logout">
        Выйти из аккаунта
      </a>
    </div>
    <div class="right-header-block" v-if="!isAuthenticated">
      <a class="header-btn" href="/auth">
        Авторизация
      </a>
      <a class="header-btn" href="/reg">
        Регистрация
      </a>
    </div>
  </header>
</template>

<style scoped>
 #logo {
   width: 40vh;
   position: absolute;
   margin: 0;
   padding: 0;
   color: #f9f1e9;
   text-align: center;
   top: 50%;
   right: 10%;
   -webkit-transform: translate3d(-50%, -50%, 0);
   transform: translate3d(-50%, -50%, 0);
 }

 img {
   width: 30vh;
 }

 img:hover {
   transform: rotate(-360deg);
   transition: all 1s;
 }
</style>