import { StyleSheet, View, Text, Button } from 'react-native';
import { multiply, execAsyncCommand } from 'react-native-shell-tools';

const result = multiply(3, 7);

const handlePress = () => {
  execAsyncCommand(`cat /sys/class/qcom-battery/soh`)
    .then((res) => {
      console.log('execCommand', res);
    })
    .catch((err) => {
      console.log(err, 'err');
    });
};

export default function App() {
  return (
    <View style={styles.container}>
      <Text>Result: {result}</Text>
      <Button title="测试" onPress={() => handlePress()} />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
