# [Java Socket](https://github.com/happyflyer/Java-Socket)

- [Java Socket 应用---通信是这样练成的](https://www.imooc.com/learn/161)

## 1. 网络基础知识

Socket = IP 地址 + 端口号

**Socket** 是网络上运行的程序之间双向通信链路的终结点，是 TCP 和 UDP 的基础。

- ftp：`21`
- telent：`23`
- http：`80`
- ...

针对网络通信的不同层次， Java 提供的网络功能的四大类：

- `InetAddress`：用于标识网络上的硬件资源
- `URL`：统一资源定位符，通过 URL 可以直接读取或写入网络上的资源
- `Socket`：使用 TCP 协议实现网络通信的 Socket 相关的类
- `Datagram`：使用 UDP 协议，数据保存数据报中，通过网络进行通信

## 2. InetAddress 类

```java
InetAddress address = InetAddress.getLocalHost();
System.out.println(address.getHostName());
System.out.println(address.getHostAddress());
System.out.println(Arrays.toString(address.getAddress()));
System.out.println(address);
```

## 3. URL

```java
try {
    URL url = new URL("https://www.baidu.com");
    InputStream is = url.openStream();
    InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
    BufferedReader br = new BufferedReader(isr);
    String data = br.readLine();
    while (data != null) {
        System.out.println(data);
        data = br.readLine();
    }
    br.close();
    isr.close();
    is.close();
} catch (IOException e) {
    e.printStackTrace();
}
```

## 4. Socket

TCP 协议(传输控制协议)是面向连接、可靠的、有序的、以字节流发送数据。

Java 中基于 TCP 协议实现网络通信的类

- 客户端的 `Socket` 类
- 服务端的 `ServerSocket` 类

![Socket通信模型](https://cdn.jsdelivr.net/gh/happyflyer/picture-bed@main/2021/Socket通信模型.gc1jqbig15s.jpg)

Socket 通信实现步骤

1. 创建 `ServerSocket` 和 `Socket`
2. 打开连接到 `Socket` 的输入/输出流
3. 按照协议对 `Socket` 进行读写操作
4. 关闭输入/输出流，关闭 `Socket`

### 4.1. 基于 TCP 的 Socket 通信

#### 4.1.1. 服务端

1. 创建 `ServerSocket` 对象，绑定监听端口
2. 通过 `accept()` 方法监听客户端请求
3. 连接建立后，通过输入流读取客户端发送的请求信息
4. 通过输出流向客户端发送响应信息
5. 关闭相应资源

```java
// 1. 创建 ServerSocket 对象，绑定监听端口
ServerSocket serverSocket = new ServerSocket(8888);
System.out.println("***服务器已经启动，开始侦听客户端的连接***");
// 2. 通过 accept() 方法监听客户端请求
Socket socket = serverSocket.accept();
// 3. 连接建立后，通过输入流读取客户端发送的请求信息
InputStream is = socket.getInputStream();
InputStreamReader isr = new InputStreamReader(is);
BufferedReader br = new BufferedReader(isr);
String info = null;
while ((info = br.readLine()) != null) {
    System.out.println("我是服务器，客户端说：" + info);
}
socket.shutdownInput(); // 关闭输入流
// 4. 通过输出流向客户端发送响应信息
OutputStream os = socket.getOutputStream();
PrintWriter pw = new PrintWriter(os);
pw.write("欢迎你");
pw.flush();
// 5. 关闭相应资源
pw.close();
os.close();
br.close();
isr.close();
is.close();
socket.close();
serverSocket.close();
```

#### 4.1.2. 客户端

1. 创建 `Socket` 对象，指明需要连接的服务的地址和端口号
2. 连接建立后，通过输出流向服务发送请求信息
3. 通过输入流获取服务响应的信息
4. 关闭相应资源

```java
// 1. 创建 Socket 对象，指明需要连接的服务器的地址和端口号
Socket socket = new Socket("127.0.0.1", 8888);
// 2. 连接建立后，通过输出流向服务器发送请求信息
OutputStream os = socket.getOutputStream();
PrintWriter pw = new PrintWriter(os);
pw.write("user:zhangsan;passwd:123456");
pw.flush();
socket.shutdownOutput(); // 关闭输出流
// 3. 通过输入流获取服务器响应的信息
InputStream is = socket.getInputStream();
InputStreamReader isr = new InputStreamReader(is);
BufferedReader br = new BufferedReader(isr);
String resp = null;
while ((resp = br.readLine()) != null) {
    System.out.println("我是客户端，服务器说：" + resp);
}
socket.shutdownInput(); // 关闭输入流
// 4. 关闭相应资源
pw.close();
os.close();
br.close();
isr.close();
is.close();
socket.close();
```

### 4.2. 使用多线程实现服务端与多客户端通信

1. 服务端创建 `ServerSocket` ，循环调用 `accept()` 等待客户端连接
2. 客户端创建一个 `Socket` 并请求和服务端连接
3. 服务端接受客户端请求，创建 `Socket` 与该客户端建立专线连接
4. 建立连接的两个 `Socket` 在一个单独的 x 线程上对话
5. 服务端继续等待新的连接

```java
// 1. 创建 ServerSocket 对象，绑定监听端口
ServerSocket serverSocket = new ServerSocket(8888);
System.out.println("***服务器已经启动，开始侦听客户端的连接***");
int count = 0;
// 2. 通过 accept() 方法监听客户端请求
Socket socket;
while (true) {
    socket = serverSocket.accept();
    ServerThread thread = new ServerThread(socket);
    thread.setPriority(4);
    thread.start();
    count++;
    System.out.println("客户端的数量为：" + count);
    InetAddress address = socket.getInetAddress();
    System.out.println("当前客户端的地址为：" + address.getHostAddress());
}
```

## 5. UDP 编程

UDP 协议(用户数据报协议)是无连接、不可靠的、无序的。

UDP 协议传输速度快。

UDP 协议以**数据报**作为数据传输的载体。

使用 UDP 协议进行数据传输时，

- 首先需要将要传输的数据定义成数据报(Datagram)
- 在数据报中指明数据所要达到的 Socket(主机和端口号)
- 然后再将数据报发送出去

- `DatagramPacket`：数据报包
- `DatagramSocket`：进行端到端通信的类

### 5.1. 基于 UDP 的 Socket 通信

#### 5.1.1. 服务端

1. 创建 `DatagramSocket`，指定端口号
2. 创建 `DatagramPacket`
3. 接受客户端发送的数据信息
4. 读取数据

```java
// 1. 创建 DatagramSocket，指定端口号
DatagramSocket socket = new DatagramSocket(8888);
// 2. 创建 DatagramPacket
byte[] data = new byte[1024];
DatagramPacket packet = new DatagramPacket(data, data.length);
System.out.println("***服务器已经启动，开始侦听客户端的连接***");
// 3. 接受客户端发送的数据信息
// 此方法在接收到数据之前按一直会保持阻塞
socket.receive(packet);
// 4. 读取数据
String info = new String(data, 0, packet.getLength());
System.out.println("我是服务器，客户端说：" + info);
// 服务器向客户端进行响应
InetAddress address = packet.getAddress();
int port = packet.getPort();
byte[] resp = "你好，这是服务器对客户端的响应".getBytes();
DatagramPacket respPacket = new DatagramPacket(resp, resp.length, address, port);
socket.send(respPacket);
socket.close();
```

#### 5.1.2. 客户端

1. 定义发送信息
2. 创建 `DatagramPacket`，包含所要发送的信息
3. 创建 `DatagramSocket`
4. 发送数据

```java
// 1. 定义发送信息
InetAddress address = InetAddress.getLocalHost();
int port = 8888;
byte[] data = "user:zhangsan;passwd:888888".getBytes();
// 2. 创建 DatagramPacket，包含所要发送的信息
DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
// 3. 创建 DatagramSocket
DatagramSocket socket = new DatagramSocket();
// 4. 发送数据
socket.send(packet);
// 客户端接收服务器端的响应
byte[] resp = new byte[1024];
DatagramPacket respPacket = new DatagramPacket(resp, resp.length);
socket.receive(respPacket);
String info = new String(resp, 0, respPacket.getLength());
System.out.println("我是客户端，服务器响应的信息为：" + info);
```

## 6. 相关问题

1. 线程优先级
2. 对于同一个 `Socket`
   - 如果关闭了输出流，则与该输出流关联的 `Socket` 也会被关闭
   - 所以一般不需要关闭流，直接关闭 `Socket` 即可
3. TCP 传输字符串、对象
4. `Socket` 传递文件
