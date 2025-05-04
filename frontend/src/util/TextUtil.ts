export const capitalizeFirstLetter = (text: string) => {
  if (text.length > 1) {
    return text.charAt(0).toUpperCase() + text.slice(1);
  }
  if (text.length === 1) {
    return text.charAt(0).toUpperCase();
  }
  return '';
}
