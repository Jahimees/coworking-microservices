<script setup>
import {onMounted} from "vue";
import initFunc from "@/scripts/Neurolinks.js";

onMounted(() => {

  initFunc.initAll()
})
</script>

<template>
  <header id="large-header" class="large-header">
    <canvas id="demo-canvas"></canvas>
    <div class="center-header-block">
      <h1>GoCow</h1>
      <div>Программное средство для бла бла бла бла бла</div>
    </div>
    <div class="right-header-block">
      <a class="header-btn" href="/auth">
        Авторизация
      </a>
      <a class="header-btn" href="#">
        Регистрация
      </a>
    </div>
  </header>
</template>

<style scoped>

</style>