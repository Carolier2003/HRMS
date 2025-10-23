import { LockOutlined, UserOutlined } from '@ant-design/icons';
import { LoginForm, ProFormText } from '@ant-design/pro-components';
import { message } from 'antd';
import { login } from '@/services/api';
import './index.less';

export default () => {
  const handleSubmit = async (values: API.LoginRequest) => {
    try {
      const response = await login(values);
      if (response.code === 200) {
        const { accessToken, refreshToken, userId, username, realName, roles } = response.data;
        
        // 保存 token
        localStorage.setItem('accessToken', accessToken);
        localStorage.setItem('refreshToken', refreshToken);
        localStorage.setItem('userInfo', JSON.stringify({ userId, username, realName, roles }));
        
        message.success('登录成功');
        
        // 使用硬刷新跳转，确保重新加载 initialState
        window.location.href = '/';
      } else {
        message.error(response.msg || '登录失败');
      }
    } catch (error) {
      message.error('登录失败，请检查网络连接');
    }
  };

  return (
    <div className="login-container">
      <LoginForm
        title="HR 管理系统"
        subTitle="人事管理系统"
        onFinish={async (values) => {
          await handleSubmit(values as API.LoginRequest);
        }}
      >
        <ProFormText
          name="username"
          fieldProps={{
            size: 'large',
            prefix: <UserOutlined />,
          }}
          placeholder="用户名: admin / hr001 / emp001"
          rules={[
            {
              required: true,
              message: '请输入用户名',
            },
          ]}
        />
        <ProFormText.Password
          name="password"
          fieldProps={{
            size: 'large',
            prefix: <LockOutlined />,
          }}
          placeholder="密码: 123456"
          rules={[
            {
              required: true,
              message: '请输入密码',
            },
          ]}
        />
      </LoginForm>
    </div>
  );
};

