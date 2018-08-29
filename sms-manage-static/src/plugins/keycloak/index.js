import store from '../../store'

let keycloak = store.state.keycloak;

export default next => {
  keycloak.init({
    onLoad: 'login-required'
  }).then(authenticated => {

    if (!authenticated) {
      window.location.reload();
    } else {
      store.dispatch('updateKeycloak', keycloak);

      setInterval(() => {
        //Token 过期，需要刷新Token
        keycloak.updateToken(30) //剩30秒刷新
          .then(refreshed => {
            if (refreshed) {
              store.dispatch('updateKeycloak', keycloak);
            } else {
              console.log('Token not refreshed, valid for '
                + Math.round(keycloak.tokenParsed.exp
                  + keycloak.timeSkew
                  - new Date().getTime() / 1000) + ' seconds')
            }
          }).catch(() => {
          console.log('Failed to refresh the token');
        });
      }, 290000);

      next();
    }

  }).catch(error => {
    console.error('failed to login');
  });
}

