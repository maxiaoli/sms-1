import { COLLAPSE, TOGGLE_COLLAPSE } from '../types'
import { ViewStorage } from '../../storage'

const view = {
  state: {
    sider: {
      collapsed: ViewStorage.getCollapsed()
    }
  },
  mutations: {
    [COLLAPSE]: (state, collapsed = null) => {
      if (null != collapsed) {
        ViewStorage.setCollapsed(collapsed);
        state.sider.collapsed = collapsed;
      } else {
        if (state.sider.collapsed) {
          ViewStorage.setCollapsed(false);
        } else {
          ViewStorage.setCollapsed(true);
        }
        state.sider.collapsed = !state.sider.collapsed;
      }
    }
  },
  actions: {
    [TOGGLE_COLLAPSE]: ({commit}, collapsed = null) => {
      commit(COLLAPSE, collapsed);
    }
  }
};

export default view