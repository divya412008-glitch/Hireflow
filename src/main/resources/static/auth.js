(function () {

    "use strict";

    const AUTH_KEY = "hireflow_authenticated";
    const USER_KEY = "hireflow_user";

    const page =
        window.location.pathname
            .split("/")
            .pop()
            .toLowerCase() || "index.html";

    const isLoginPage = page === "login.html";


    // =====================================================
    // AUTH CHECK
    // =====================================================

    const authenticated =
        sessionStorage.getItem(AUTH_KEY) === "true";


    if (!isLoginPage && !authenticated) {

        window.location.replace("login.html");

        return;
    }


    if (isLoginPage && authenticated) {

        window.location.replace("index.html");

        return;
    }


    // =====================================================
    // USER
    // =====================================================

    function getUser() {

        try {

            return JSON.parse(
                sessionStorage.getItem(USER_KEY)
            );

        } catch {

            return null;

        }

    }


    // =====================================================
    // LOGOUT
    // =====================================================

    function logout() {

        sessionStorage.removeItem(AUTH_KEY);
        sessionStorage.removeItem(USER_KEY);

        window.location.replace("login.html");

    }


    // =====================================================
    // GLOBAL AUTH OBJECT
    // =====================================================

    window.HireFlowAuth = {

        isAuthenticated: function () {

            return sessionStorage.getItem(AUTH_KEY) === "true";

        },

        getUser: function () {

            return getUser();

        },

        logout: function () {

            logout();

        }

    };


    // =====================================================
    // LOGOUT BUTTON
    // =====================================================

    function createLogoutButton() {

        if (!authenticated) {
            return;
        }


        if (document.getElementById("hireflowLogoutButton")) {
            return;
        }


        const button =
            document.createElement("button");

        button.id =
            "hireflowLogoutButton";

        button.type =
            "button";

        button.innerHTML =
            "↪ Logout";


        button.style.position =
            "fixed";

        button.style.top =
            "18px";

        button.style.right =
            "20px";

        button.style.zIndex =
            "99999";

        button.style.padding =
            "10px 16px";

        button.style.border =
            "1px solid rgba(255,255,255,0.12)";

        button.style.borderRadius =
            "12px";

        button.style.background =
            "rgba(15,18,34,0.92)";

        button.style.color =
            "#dce2f2";

        button.style.fontSize =
            "12px";

        button.style.fontWeight =
            "700";

        button.style.cursor =
            "pointer";

        button.style.backdropFilter =
            "blur(12px)";

        button.style.boxShadow =
            "0 10px 30px rgba(0,0,0,0.28)";


        button.addEventListener(
            "mouseenter",
            function () {

                button.style.background =
                    "rgba(255,70,100,0.13)";

                button.style.color =
                    "#ff9aaa";

            }
        );


        button.addEventListener(
            "mouseleave",
            function () {

                button.style.background =
                    "rgba(15,18,34,0.92)";

                button.style.color =
                    "#dce2f2";

            }
        );


        button.addEventListener(
            "click",
            function () {

                const ok =
                    window.confirm(
                        "Do you want to logout?"
                    );

                if (ok) {

                    logout();

                }

            }
        );


        document.body.appendChild(button);

    }


    // =====================================================
    // PROFILE UPDATE
    // =====================================================

    function updateProfile() {

        const user =
            getUser();

        if (!user) {
            return;
        }


        const nameElement =
            document.querySelector(
                ".profile-info strong"
            );

        const workspaceElement =
            document.querySelector(
                ".profile-info span"
            );

        const avatarElement =
            document.querySelector(
                ".profile-avatar"
            );


        if (nameElement && user.name) {

            nameElement.textContent =
                user.name;

        }


        if (workspaceElement) {

            workspaceElement.textContent =
                user.role
                    ? user.role + " • HireFlow"
                    : "HireFlow Workspace";

        }


        if (avatarElement && user.name) {

            avatarElement.textContent =
                user.name
                    .charAt(0)
                    .toUpperCase();

        }

    }


    // =====================================================
    // INITIALIZE
    // =====================================================

    function initialize() {

        if (!authenticated) {
            return;
        }

        createLogoutButton();

        updateProfile();

    }


    if (
        document.readyState === "loading"
    ) {

        document.addEventListener(
            "DOMContentLoaded",
            initialize
        );

    } else {

        initialize();

    }


})();