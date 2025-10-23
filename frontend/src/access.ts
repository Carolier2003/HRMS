/**
 * 权限守卫配置
 */
export default function access(initialState: { currentUser?: API.CurrentUser } | undefined) {
  const { currentUser } = initialState ?? {};
  
  const hasRole = (role: string) => {
    return currentUser?.roles?.includes(role);
  };
  
  return {
    // 是否可以管理员工（HR 和 ADMIN）
    canManageEmployee: hasRole('HR') || hasRole('ADMIN'),
    
    // 是否可以管理工资（HR 和 ADMIN）
    canManageSalary: hasRole('HR') || hasRole('ADMIN'),
    
    // 是否可以查看统计（HR 和 ADMIN）
    canViewStatistics: hasRole('HR') || hasRole('ADMIN'),
    
    // 是否是管理员
    isAdmin: hasRole('ADMIN'),
  };
}

