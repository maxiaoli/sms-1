import './keycloak'
import config from '../../config/config'

var keycloak = Keycloak({
  url: config.keycloak.url,
  realm: config.keycloak.realm,
  clientId: config.keycloak.clientId
});

keycloak.init({
  onLoad: 'login-required'
}).then(function(authenticated) {
  console.log(authenticated ? 'authenticated' : 'not authenticated');
}).catch(function() {
  console.log('failed to initialize');
});

keycloak.updateToken(30).then(function() {
}).catch(function() {
  console.log('Failed to refresh token');
});

export default keycloak
