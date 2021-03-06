package link.myrecipes.api.dto.security;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserSecurity {

    private Integer id;

    private String username;

    private String password;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private boolean enabled;

    private List<UserRoleSecurity> userRoleSecurityList = new ArrayList<>();

    @Builder
    public UserSecurity(Integer id, String username, String password, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled) {

        this.id = id;
        this.username = username;
        this.password = password;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
    }

    public void addUserRoleSecurity(UserRoleSecurity userRoleSecurity) {
        this.userRoleSecurityList.add(userRoleSecurity);
    }
}
