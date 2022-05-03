import { createRouter, createWebHistory } from 'vue-router'

const routes = [{
        path: '/',
        redirect: '/home',
        name: 'home',
        component: () =>
            import ('../views/index'),

        children: [{
                path: "Login",
                name: "Login",
                component: () =>
                    import ('../views/Login'),
            },
            {
                path: "Register",
                name: "Register",
                component: () =>
                    import ('../views/Register'),
            }
        ]
    },
    {
        path: "/Home",
        name: "HomePage",
        component: () =>
            import ('../views/Home'),
    },
    {
        path: "/Pages",
        name: "Page",
        component: () =>
            import ('../views/Pages'),
    }


]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router