import { message, Button, Dropdown } from 'antd';
import { history } from '@umijs/max';
import { getCurrentUser, refreshToken } from './services/api';
import { UserOutlined, LogoutOutlined } from '@ant-design/icons';

/**
 * 全局初始状态配置
 */
export async function getInitialState(): Promise<{
  currentUser?: API.CurrentUser;
}> {
  const token = localStorage.getItem('accessToken');
  
  if (!token) {
    return {};
  }
  
  try {
    const currentUser = await getCurrentUser();
    return {
      currentUser,
    };
  } catch (error) {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    return {};
  }
}

/**
 * 布局运行时配置
 */
export const layout = ({ initialState }: any) => {
  return {
    onPageChange: () => {
      const { location } = history;
      // 如果没有登录，重定向到 login
      if (!initialState?.currentUser && location.pathname !== '/login') {
        history.push('/login');
      }
    },
    rightContentRender: () => {
      const handleLogout = () => {
        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');
        message.success('退出登录成功');
        history.push('/login');
      };

      const menuItems = [
        {
          key: 'logout',
          icon: <LogoutOutlined />,
          label: '退出登录',
          onClick: handleLogout,
        },
      ];

      return (
        <div style={{ marginRight: 16, display: 'flex', alignItems: 'center', gap: '8px' }}>
          <UserOutlined style={{ color: '#1890ff' }} />
          <span style={{ marginRight: 8 }}>
            {initialState?.currentUser?.realName || initialState?.currentUser?.username}
          </span>
          <Dropdown
            menu={{ items: menuItems }}
            placement="bottomRight"
            arrow
          >
            <Button type="text" size="small">
              退出
            </Button>
          </Dropdown>
        </div>
      );
    },
  };
};

/**
 * 全局请求拦截器配置
 */
export const request = {
  timeout: 10000,
  errorConfig: {
    errorHandler: async (error: any) => {
      const { response } = error;
      if (response?.status === 401) {
        // 尝试使用refresh token刷新
        const refreshTokenValue = localStorage.getItem('refreshToken');
        if (refreshTokenValue) {
          try {
            const refreshResponse = await refreshToken(refreshTokenValue);
            if (refreshResponse.code === 200) {
              localStorage.setItem('accessToken', refreshResponse.data.accessToken);
              localStorage.setItem('refreshToken', refreshResponse.data.refreshToken);
              message.success('登录状态已刷新');
              // 重新发起原请求
              return;
            }
          } catch (refreshError) {
            console.error('Token refresh failed:', refreshError);
          }
        }
        
        // 刷新失败或没有refresh token，跳转到登录页
        message.error('登录已过期，请重新登录');
        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');
        history.push('/login');
      } else if (response?.status === 403) {
        message.error('权限不足');
      } else {
        message.error(error.message || '请求失败');
      }
    },
  },
  requestInterceptors: [
    (url: string, options: any) => {
      const token = localStorage.getItem('accessToken');
      if (token) {
        options.headers = {
          ...options.headers,
          Authorization: `Bearer ${token}`,
        };
      }
      return { url, options };
    },
  ],
};

