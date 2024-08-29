import './assets/main.css'
import './assets/common.css'
import './assets/header.css'
import './assets/normalize.css'
import './assets/base.css'

import './scripts/TweenLite.min.js'
import './scripts/EasePack.min.js'
import "./scripts/Utils.js"


// import "http://html5shiv.googlecode.com/svn/trunk/html5.js"
import {createApp} from 'vue'
import App from './App.vue'
import AuthView from "@/components/views/AuthView.vue";
import RegView from "@/components/views/RegView.vue";
import HomeView from "@/components/views/HomeView.vue";
import DrawPanelView from "@/components/views/DrawPanelView.vue";
import PersonalAccountView from "@/components/views/PersonalAccountView.vue"

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
        },
        {
            path: "/draw",
            component: DrawPanelView
        },
        {
            path: "/reg",
            component: RegView
        },
        {
            path: "/account",
            component: PersonalAccountView
        }
    ],
    history: createWebHistory()
})

createApp(App)
    .use(router)
    .mount('#app')
