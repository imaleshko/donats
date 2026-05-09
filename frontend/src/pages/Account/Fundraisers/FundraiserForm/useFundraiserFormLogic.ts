import { type ChangeEvent, useEffect, useState } from "react";
import {
  INITIAL_FORM,
  type NewImageEntry,
  validate,
} from "./fundraiserFormUtils";

export const useFundraiserFormLogic = () => {
  const [formData, setFormData] = useState(INITIAL_FORM);
  const [retainedImages, setRetainedImages] = useState<string[]>([]);
  const [newImages, setNewImages] = useState<NewImageEntry[]>([]);
  const [errors, setErrors] = useState<Record<string, string>>({});

  useEffect(() => {
    return () => {
      newImages.forEach((img) => URL.revokeObjectURL(img.previewUrl));
    };
  }, [newImages]);

  const onFieldChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setErrors((prev) => ({ ...prev, [name]: "" }));
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const onDescriptionChange = (value?: string) => {
    setErrors((prev) => ({ ...prev, description: "" }));
    setFormData((prev) => ({ ...prev, description: value || "" }));
  };

  const onAddFiles = (files: FileList | null) => {
    if (!files) return;
    const entries = Array.from(files)
      .filter((file) => file.type.startsWith("image/"))
      .map((file) => ({ file, previewUrl: URL.createObjectURL(file) }));
    setNewImages((prev) => [...prev, ...entries]);
  };

  const onRemoveRetainedImage = (urlToRemove: string) => {
    setRetainedImages((prev) => prev.filter((url) => url !== urlToRemove));
  };

  const onRemoveNewImage = (index: number) => {
    setNewImages((prev) => {
      URL.revokeObjectURL(prev[index].previewUrl);
      return prev.filter((_, index) => index !== index);
    });
  };

  const validateForm = () => {
    const validationErrors = validate(formData);
    if (Object.keys(validationErrors).length) {
      setErrors(validationErrors);
      return false;
    }
    return true;
  };

  return {
    formData,
    errors,
    retainedImages,
    newImages,
    onFieldChange,
    onDescriptionChange,
    onAddFiles,
    onRemoveRetainedImage,
    onRemoveNewImage,
    setFormData,
    setRetainedImages,
    validateForm,
  };
};
