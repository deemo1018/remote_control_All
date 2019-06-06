import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/Index.vue'
import Control from '@/views/Control.vue'
import Watch from '@/views/Watch.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'index',
      component: Home,
    },
    {
      path:'/control',
      name: 'control',
      component:Control
    }, {
      path:'/watch',
      name: 'watch',
      component:Watch
    }
  ]
})
