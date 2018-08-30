
const getters = {
  keycloak: state => state.user.keycloak,
  username: (state) => {
    try {
      //eagerly get newest user username
      if (state.user.keycloak.tokenParsed) {
        state.user.username = state.user.keycloak.tokenParsed.preferred_username
      }
    } catch (e) {}
    return state.user.username
  },
  name: (state) => {
    try {
      //eagerly get newest user name
      if (state.user.keycloak.tokenParsed) {
        state.user.name = state.user.keycloak.tokenParsed.name
      }
    } catch (e) {}
    return state.user.name;
  }
};

export default getters