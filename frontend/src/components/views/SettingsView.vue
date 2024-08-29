<script setup>

  const props = defineProps({parsedToken: Object})
</script>

<template>
  <div class="inline-block">
    {{ props.parsedToken }}
    <div>
      <div>Главные настройки</div>
      <div>
        <label>Имя пользователя</label>
        <input>
      </div>
      <div>
        <label>Электронная почта</label>
        <input>
      </div>
      <div>
        <label>Пароль</label>
        <input>
      </div>
      <div>

      </div>
    </div>
    <div>
      <div>Информация</div>
      <div>
        <label>Имя</label>
        <input>
      </div>
      <div>
        <label>Фамилия</label>
        <input>
      </div>
      <div>
        <label>Отчество</label>
        <input>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>