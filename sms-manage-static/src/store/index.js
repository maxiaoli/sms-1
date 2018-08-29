import Vue from 'vue'
import Vuex from 'vuex'
import axios from '../plugins/axios'
import { setKeycloak, removeKeycloak, setUsername, getUsername, getName, setName} from './modules/auth'
import Keycloak from "keycloak-js";
import config from '../../config'

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    keycloak: new Keycloak({
      url: config.keycloak.authUrl,
      realm: config.keycloak.realm,
      clientId: config.keycloak.clientId
    }),
    user: {
      username: getUsername(),
      name: getName()
    }
  },
  getters: {
    keycloak: state => state.keycloak,
    username: state => state.user.username,
    name: state => state.user.name
  },
  mutations: {
    KEYCLOAK: (state, keycloak, deleted = false) => {
      if (deleted) {
        setUsername(null);
        removeKeycloak();
      } else {
        try {
          state.keycloak = keycloak;
        } catch (ex) {}
        
        try {
          state.user.username = keycloak.tokenParsed.preferred_username;
          state.user.name = keycloak.tokenParsed.name
        } catch (e) {}

        setKeycloak(keycloak);
        setUsername(state.user.username);
        setName(state.user.name);
        axios.defaults.headers.common = { 'Authorization': 'Bearer ' + keycloak.token }
      }
      state.keycloak = keycloak;
    }
  },
  actions: {
    updateKeycloak: ({ commit }, keycloak, deleted = false) => {
        commit('KEYCLOAK', keycloak, deleted);
    },
    logout: ({ commit }) => {
      commit('KEYCLOAK', null, true);
    }
  }
})
