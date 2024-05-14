/* eslint-disable */
export const execute = (fn) => {
  fn().catch((e) => {
    console.error(e.stack ? e.stack : String(e));
    process.exit(1);
  });
};
