# react-native-shell-tools

Library For Android Device Shell Tools

## Installation

```sh
npm install react-native-shell-tools
```

## Usage


```js
import { execCommand, execAsyncCommand } from 'react-native-shell-tools';

// 
  // 异步用法
  execAsyncCommand(`cat /sys/class/qcom-battery/soh`)
    .then((res) => {
      console.log('execAsyncCommand', res);
    })
    .catch((err) => {
      console.log(err, 'execAsyncCommandErr');
    });
    // 同步用法
    const batteryRes = execCommand(`cat /sys/class/qcom-battery/soh`)
    console.log(batteryRes)
```


## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT

---

Made with [create-react-native-library](https://github.com/callstack/react-native-builder-bob)
