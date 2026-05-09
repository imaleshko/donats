import styles from "./Description.module.css";
import MDEditor from "@uiw/react-md-editor";

interface DescriptionProps {
  description: string;
}

const Description = ({ description }: DescriptionProps) => {
  return (
    <div className={styles.wrapper}>
      <h2 className={styles.title}>Опис збору</h2>

      <MDEditor.Markdown
        source={description}
        style={{
          backgroundColor: "transparent",
          color: "var(--text-grey-color)",
        }}
      />
    </div>
  );
};

export default Description;
