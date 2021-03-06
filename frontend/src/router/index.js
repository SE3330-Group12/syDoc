import { createRouter, createWebHistory } from 'vue-router'

const routes = [
    {
    path: '/Login',
    name: 'Login',
    component: () =>
        import ('../views/Login'),

    // children: [{
    //     path: "Login",
    //     name: "Login",
    //     component: () =>
    //         import ('../views/Login'),
    // },
    //     // {
    //     //     path: "Register",
    //     //     name: "Register",
    //     //     component: () =>
    //     //         import ('../views/Register'),
    //     // }
    // ]
},
    {
        path: "/Register",
        name: "Register",
        component: () =>
            import ('../views/Register'),
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
    },
    {
        path: "/Personal",
        name: "Personal Center",
        component: () =>
            import ('../views/Personal Center'),
    },
    {
        path:'/CovLetter',
        name:"CovLetterTemplate",
        component: () =>
            import('../views/CovLetterTemplate'),
    },
    {
        path:'/Resume',
        name:"ResumeTemplate",
        component: () =>
            import('../views/ResumeTemplate'),
    },
    {
        path: '/Excel',
        name:"ExcelPage",
        component: () =>
            import('../views/ExcelPage'),
    },
    {
        path: "/Help",
        name: "Help",
        component: () =>
            import ('../views/Help'),
    },
    {
        path: '/',
        redirect: '/Login',
    },
    


]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router