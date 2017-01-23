一、XML 文件
    1、Pull 解析步骤：
     （1）获取 XmlPullParserFactory 的实例：
          XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
     （2）获得 XmlPullParser 的实例：
          XmlPullParser xmlPullParser = factory.newPullParser();
     （3）设置服务器返回的 XML 解析数据进 XmlPullParser：
          xmlPullParser.setInput(new StringReader(xmlData));
     （4）解析 XML 数据

      /*******示例代码如下：********/
      private void parseXMLWithPull(String xmlData) {
          try {
              XmlPullParserFactory factory = XmlPullParserFactory.newInstance();//获取 XmlPullParserFactory 的实例
              XmlPullParser xmlPullParser = factory.newPullParser();//获得 XmlPullParser 的实例
              xmlPullParser.setInput(new StringReader(xmlData));//设置服务器返回的 XML 解析数据进 XmlPullParser
              int eventType = xmlPullParser.getEventType();//得到当前的解析事件
              String id = "";
              String name = "";
              String version = "";
              while (eventType != XmlPullParser.END_DOCUMENT) {//相等时表示解析结束
                  String nodeName = xmlPullParser.getName();//获取某个节点名
                  switch (eventType) {
                      // 开始解析某个结点
                      case XmlPullParser.START_TAG: {
                          if ("id".equals(nodeName)) {
                              id = xmlPullParser.nextText();//获取节点内具体的内容
                          } else if ("name".equals(nodeName)) {
                              name = xmlPullParser.nextText();
                          } else if ("version".equals(nodeName)) {
                              version = xmlPullParser.nextText();
                          }
                          break;
                      }
                      // 完成解析某个结点时,打印节点信息
                      case XmlPullParser.END_TAG: {
                          if ("app".equals(nodeName)) {
                              Log.d("MainActivity", "id is " + id);
                              Log.d("MainActivity", "name is " + name);
                              Log.d("MainActivity", "version is " + version);
                          }
                          break;
                      }
                      default:
                          break;
                  }
                  eventType = xmlPullParser.next();//获取下一个解析事件
              }
          } catch (Exception e) {
              e.printStackTrace();
          }
      }
    2、SAX 解析步骤：
     （1）新建一个类继承 DefaultHandler,并重写父类的五个方法：
          public class ContentHandler extends DefaultHandler {

               private String nodeName;
               private StringBuilder id;
               private StringBuilder name;
               private StringBuilder version;
               @Override
               //XML解析时调用
               public void startDocument() throws SAXException {
                   id = new StringBuilder();
                   name = new StringBuilder();
                   version = new StringBuilder();
               }
               @Override
               //开始解析某个节点时调用
               public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                   // 记录当前结点名
                   nodeName = localName;
               }
               @Override
               //获取节点中内容时调用
               public void characters(char[] ch, int start, int length) throws SAXException {
                   // 根据当前的结点名判断将内容添加到哪一个StringBuilder对象中
                   if ("id".equals(nodeName)) {
                       id.append(ch, start, length);
                   } else if ("name".equals(nodeName)) {
                       name.append(ch, start, length);
                   } else if ("version".equals(nodeName)) {
                       version.append(ch, start, length);
                   }
               }
               @Override
               //完成解析某个节点时调用
               public void endElement(String uri, String localName, String qName) throws SAXException {
                   if ("app".equals(localName)) {
                       Log.d("ContentHandler", "id is " + id.toString().trim());
                       Log.d("ContentHandler", "name is " + name.toString().trim());
                       Log.d("ContentHandler", "version is " + version.toString().trim());
                       // 最后要将StringBuilder清空掉
                       id.setLength(0);
                       name.setLength(0);
                       version.setLength(0);
                   }
               }
               @Override
               //完成整个XML文件解析时调用
               public void endDocument() throws SAXException {
                   super.endDocument();
               }
          }
     （2）解析 XML 数据：
          private void parseXMLWithSAX(String xmlData) {
              try {
                  SAXParserFactory factory = SAXParserFactory.newInstance();//创建一个SAXParserFactory的对象
                  XMLReader xmlReader = factory.newSAXParser().getXMLReader();//获取到XMLReader对象
                  ContentHandler handler = new ContentHandler();
                  xmlReader.setContentHandler(handler);//将ContentHandler的实例设置到XMLReader中
                  xmlReader.parse(new InputSource(new StringReader(xmlData)));//开始执行解析
              } catch (Exception e) {
                  e.printStackTrace();
              }
          }
    3、DOM 解析步骤：
      对于DOM而言，XML文档中每一个成分都是一个节点。
  　　 DOM是这样规定的：
        a.整个文档是一个文档节点。
        b.每一个XML标签是一个元素节点。
        c.包含在XML元素中的文本是一个文本节点。
        d.每一个XML属性是一个属性节点。

      （1）首先通过 DocumentBuilderFactory 这个类来构建一个解析工厂类,通过newInstance()的方法可以得到
           一个 DocumentBuilderFactory 的对象。
      （2）通过上面的这个工厂类创建一个 DocumentBuilder 的对象,这个类就是用来对我们的xml文档进行解析,
           通过DocumentBuilderFactory的newDocumentBuilder()方法
      （3）通过创建好的 DocumentBuilder 对象的 parse(InputStream) 方法就可以解析我们的xml文档，
          然后返回的是一个 Document 的对象，这个 Document 对象代表的就是我们的整个 XML 文档。
      （4）得到了整个 XML 的 Document 对象后，我们可以获得其下面的各个元素节点(Element)，同样每个元素节点
           可能又有多个属性(Attribute)，根据每个元素节点我们又可以遍历该元素节点下面的子节点等等。

        /*******代码示例：******/
        public class DomParserUtils
        {
            public static List<Person> parserXmlByDom(InputStream inputStream) throws Exception
            {
                List<Person> persons = new ArrayList<Person>();
                //    得到一个DocumentBuilderFactory解析工厂类
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                //    得到一个DocumentBuilder解析类
                DocumentBuilder builder = factory.newDocumentBuilder();
                //    接收一个xml的字符串来解析xml,Document代表整个xml文档
                Document document = builder.parse(inputStream);
                //    得到xml文档的根元素节点
                Element personsElement = document.getDocumentElement();
                //    得到标签为person的Node对象的集合NodeList
                NodeList nodeList = personsElement.getElementsByTagName("person");
                for(int i = 0; i < nodeList.getLength(); i++)
                {
                    Person person = new Person();
                    //    如果该Node是一个Element
                    if(nodeList.item(i).getNodeType() == Document.ELEMENT_NODE)
                    {
                        Element personElement = (Element)nodeList.item(i);
                        //    得到id的属性值
                        String id = personElement.getAttribute("id");
                        person.setId(Integer.parseInt(id));

                        //    得到person元素下的子元素
                        NodeList childNodesList = personElement.getChildNodes();
                        for(int j = 0; j < childNodesList.getLength(); j++)
                        {
                            if(childNodesList.item(j).getNodeType() == Document.ELEMENT_NODE)
                            {
                                //    解析到了person下面的name标签
                                if("name".equals(childNodesList.item(j).getNodeName()))
                                {
                                    //    得到name标签的文本值
                                    String name = childNodesList.item(j).getFirstChild().getNodeValue();
                                    person.setName(name);
                                }
                                else if("address".equals(childNodesList.item(j).getNodeName()))
                                {
                                    String age = childNodesList.item(j).getFirstChild().getNodeValue();
                                    person.setAge(Integer.parseInt(age));
                                }
                            }
                        }

                        persons.add(person);
                        person = null;
                    }
                }
                return persons;
            }
        }
二、JSON 文件
    1、JSONObeject 解析 JSON：
        /********示例代码如下：********/
        private void parseJSONWithJSONObject(String jsonData) {
            try {
                JSONArray jsonArray = new JSONArray(jsonData);//将服务器数据传入JSONArray数组对象中
                for (int i = 0; i < jsonArray.length(); i++) {//循环遍历
                    JSONObject jsonObject = jsonArray.getJSONObject(i);//取出每个JSONObject对象
                    String id = jsonObject.getString("id");
                    String name = jsonObject.getString("name");
                    String version = jsonObject.getString("version");
                    Log.d("MainActivity", "id is " + id);
                    Log.d("MainActivity", "name is " + name);
                    Log.d("MainActivity", "version is " + version);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    2、GSON 解析 JSON：
      （1）在build.gradle文件中dependencies闭包添加内容如下：
           compile 'com.squareup.okhttp3:okhttp:3.5.0'
      （2）代码示例：
        /********示例代码如下：********/
        private void parseJSONWithGSON(String jsonData) {
            Gson gson = new Gson();
            List<App> appList = gson.fromJson(jsonData, new TypeToken<List<App>>() {
            }.getType());
            for (App app : appList) {
                Log.d("MainActivity", "id is " + app.getId());
                Log.d("MainActivity", "name is " + app.getName());
                Log.d("MainActivity", "version is " + app.getVersion());
            }
        }

       /*********/
       【其独特之处在于：将一段 JSON 格式的字符串自动映射成一个对象】
       /*********/
