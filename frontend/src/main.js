import './assets/main.css'
import './assets/common.css'
import './assets/header.css'
import './assets/normalize.css'
import './assets/base.css'

import './scripts/TweenLite.min.js'
import './scripts/EasePack.min.js'
import './scripts/Neurolinks.js'


// import "http://html5shiv.googlecode.com/svn/trunk/html5.js"
import {createApp} from 'vue'
import App from './App.vue'
import AuthView from "@/components/views/AuthView.vue";
import HomeView from "@/components/views/HomeView.vue";
import {createRouter, createWebHistory} from "vue-router";

const router = createRouter({
    routes: [
        {
            path: "/",
            component: HomeView
        },
        {
            path: "/auth",
            component: AuthView
        }],
    history: createWebHistory()
})

createApp(App)
    .use(router)
    .mount('#app')
