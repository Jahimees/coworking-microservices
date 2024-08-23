var utils = {

    getCookie(name) {
        let matches = document.cookie.match(new RegExp(
            "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
        ));
        return matches ? decodeURIComponent(matches[1]) : undefined;
    },
    logout() {
        setCookie("_at", "")
        //todo fetch to block
    },

    async doInfoAction() {
        const location = window.location.href
        const paramsString = window.location.search
        const urlParams = new URLSearchParams(paramsString)
        const info = urlParams.get("i")

        if (info !== null) {
            switch (info) {
                case "reg": {
                    alert("Регистрация прошла успешно")
                    break
                }
                case "auth": {
                    alert("Авторизация прошла успешно")
                }
            }

            window.location.href = location.substring(0, location.indexOf(paramsString))
        }
    }
}

function setCookie(name, value, options = {}) {

    options = {
        path: '/',
        // при необходимости добавьте другие значения по умолчанию
        ...options
    };

    if (options.expires instanceof Date) {
        options.expires = options.expires.toUTCString();
    }

    let updatedCookie = encodeURIComponent(name) + "=" + encodeURIComponent(value);

    for (let optionKey in options) {
        updatedCookie += "; " + optionKey;
        let optionValue = options[optionKey];
        if (optionValue !== true) {
            updatedCookie += "=" + optionValue;
        }
    }

    document.cookie = updatedCookie;
}

export default utils
