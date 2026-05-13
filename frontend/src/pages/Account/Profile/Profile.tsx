import styles from "./Profile.module.css";
import DataField from "./TextFields/DataField.tsx";
import PasswordField from "./TextFields/PasswordField.tsx";
import useGetUser from "@/app/useGetUser.ts";
import useChangeEmail from "./TextFields/useChangeEmail.ts";
import useChangePassword from "./TextFields/useChangePassword.ts";
import useChangeUsername from "./TextFields/useChangeUsername.ts";
import AvatarField from "./AvatarField/AvatarField.tsx";
import { Divider } from "@/components/Divider/Divider.tsx";

const validateEmail = (value: string) =>
  !value.includes("@") ? "Некоректна пошта" : null;

const validateUsername = (value: string) =>
  value.length < 3 ? "Мінімум 3 символи" : null;

const Profile = () => {
  const { data: user } = useGetUser();
  const {
    changeEmail,
    isPending: isEmailPending,
    error: emailError,
  } = useChangeEmail();
  const {
    changeUsername,
    isPending: isUsernamePending,
    error: usernameError,
  } = useChangeUsername();
  const {
    changePassword,
    isPending: isPasswordPending,
    error: passwordError,
  } = useChangePassword();

  const handleEmailSave = (newValue: string, onSuccess: () => void) => {
    changeEmail({ email: newValue }, { onSuccess });
  };

  const handleUsernameSave = (newValue: string, onSuccess: () => void) => {
    changeUsername({ username: newValue }, { onSuccess });
  };

  const handlePasswordSave = (
    oldPassword: string,
    newPassword: string,
    onSuccess: () => void,
  ) => {
    changePassword({ oldPassword, newPassword }, { onSuccess });
  };

  if (!user) return null;

  return (
    <div className={styles.wrapper}>
      <h2 className={styles.title}>Персональні дані</h2>

      <Divider />

      <AvatarField avatarUrl={user.avatarUrl} />

      <div className={styles.fields}>
        <DataField
          label="Електронна адреса"
          value={user.email}
          type="email"
          onSave={handleEmailSave}
          isPending={isEmailPending}
          serverError={emailError}
          validate={validateEmail}
        />
        <DataField
          label="Ім'я"
          value={user.username}
          type="text"
          onSave={handleUsernameSave}
          isPending={isUsernamePending}
          serverError={usernameError}
          validate={validateUsername}
          warning="Зміна імені зробить недійсними всі посилання на збори"
        />
        <PasswordField
          onSave={handlePasswordSave}
          isPending={isPasswordPending}
          serverError={passwordError}
        />
      </div>
    </div>
  );
};

export default Profile;
