/// <reference types="@capawesome/capacitor-live-update" />
import type { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  appId: 'com.example.plugin',
  appName: 'example',
  webDir: 'dist',
  plugins: {
    LiveUpdate: {
      appId: '62d8e181-726f-45ab-aadc-15cd39bbd298',
      // publicKey:
      //   '-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoMb8yazAm5rY2ys6nQDgsKdGlQiLStdd7VBYjKKaRT2xwKXCPtsmeBtjvVUH//ae3IHbz7w79CoawKPDqKAPtqILWc3VyAR2hQ6ftoGVxYq5NyuV4UdZozEn9v45Se91gvi7Jc+NakZaPYBAG695X1d9iCdktWINqexQjZWZUEnQjdLoKSdlbtyU0GYiDTkPDUruVBx1YF7W7qOIEr5uhbPF2HKtU6VvsoKOHePfIZQa12rn0Q5s69jb0++ro1zHMZSV6eJox6RJg/7uHOKo5Ri8FRhHHZQxxbP/Pp4FTHvpfQyav2yOuq0l9mAAgzi9txWMfB1AXwZbbUceuE7UjwIDAQAB-----END PUBLIC KEY-----',
      // readyTimeout: 10000,
    },
  },
};

export default config;
