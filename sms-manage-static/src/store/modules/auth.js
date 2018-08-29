import * as constant from '../../constant'

export function setKeycloak(keycloak) {
  localStorage.setItem(constant.KEYCLOAK, keycloak);
}

export function getKeycloak() {
  let keyStr = localStorage.getItem(constant.KEYCLOAK);
  let res = (keyStr === undefined || null === keyStr || '' === keyStr) ? {authenticated: false } : keyStr;
  return res;
}

export function removeKeycloak() {
  localStorage.removeItem(constant.KEYCLOAK);
  localStorage.removeItem(constant.USERNAME);
  localStorage.removeItem(constant.NAME);
}

export function setUsername(username) {
  localStorage.setItem(constant.USERNAME, username);
}

export function getUsername() {
  return localStorage.getItem(constant.USERNAME);
}

export function setName(username) {
  localStorage.setItem(constant.NAME, username);
}

export function getName() {
  return localStorage.getItem(constant.NAME);
}