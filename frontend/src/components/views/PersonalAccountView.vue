<script setup>
import Utils from "@/scripts/Utils.js";
import {onMounted, ref} from "vue";
import SettingsView from "@/components/views/SettingsView.vue";
import CurrentOffice from "@/components/views/CurrentOffice.vue";
import OfficePeople from "@/components/views/OfficePeople.vue";
import OfficeManagement from "@/components/views/OfficeManagement.vue";

//account check fetch
Utils.redirectToAuthIfNotAuthorized()

const token = ref(Utils.getCookie('_at'))
const parsedToken = ref(Utils.parseJwt(token.value))

const isSettings = ref(true)
const isCurrentOffice = ref(false)
const isOfficePeople = ref(false)
const isOfficeManagement = ref(false)

var prevTarget = isSettings

fetch("http://localhost:8765/user-service/api/v1/test",
    {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      crossDomain: true
    }
)
    .then(async (response) => {
      console.log(response)
    })
    .then((responseJson) => {
    })
    .catch((error) => {
    });


onMounted(() => {

  const $menuBtn = $(".menu-btn");
  $menuBtn.unbind()

  $menuBtn.on("click", (e) => {
    [...$menuBtn].forEach((el) => {
      el.className = "menu-btn"
    })

    switch (e.currentTarget.id) {
      case "settings": {
        prevTarget.value = false
        isSettings.value = true
        prevTarget = isSettings
        break
      }
      case "currentOffice": {
        prevTarget.value = false
        isCurrentOffice.value = true
        prevTarget = isCurrentOffice
        break
      }
      case "officePeople": {
        prevTarget.value = false
        isOfficePeople.value = true
        prevTarget = isOfficePeople
        break
      }
      case "officeManagement": {
        prevTarget.value = false
        isOfficeManagement.value = true
        prevTarget = isOfficeManagement
        break
      }
    }

    e.currentTarget.className = "menu-btn select"
  })

})
</script>

<template>

  <div class="inline-block">
    <div style="background-color: #EAFAFCFF; height: 100vh" id="menu-bar" v-if="parsedToken.roles[0] === 'ROLE_USER'">
      <div><img src="@/assets/images/logo.png" class="block-center"></div>
      <div>Текущий офис:</div>
      <a href="/" class="menu-btn">На главную</a>
      <a href="#" id="settings" class="menu-btn" :class="{select: isSettings}">Настройки</a>
      <a href="#" id="currentOffice" class="menu-btn">Текущий офис</a>
      <a href="#" id="officePeople" class="menu-btn">Люди в офисе</a>
      <a href="#" id="officeManagement" class="menu-btn">Управление офисами</a>
      <a href="" @click="Utils.logout()" class="menu-btn">Выйти из аккаунта</a>
    </div>
    <div class="w-100">
      <SettingsView v-model:parsed-token="parsedToken" v-if="isSettings" />
      <CurrentOffice v-if="isCurrentOffice"/>
      <OfficePeople v-if="isOfficePeople" />
      <OfficeManagement v-if="isOfficeManagement" />
    </div>
  </div>

</template>

<style scoped>

img {
  width: 20vh;
  margin-top: 1em;
}

.menu-btn {
  font-family: 'codropsicons';
  font-size: 1.5em;
  background-color: #eafafc;
  padding: 10px 0;
  width: 275px;
  text-align: center;
  cursor: pointer;
  text-decoration: none;
  color: black;
}

.menu-btn:hover {
  background-color: #bddeff;
}

.menu-btn.select {
  background-color: #acdbff;
}

a {
  display: block;
}

body {
  min-height: 0;
}
</style>