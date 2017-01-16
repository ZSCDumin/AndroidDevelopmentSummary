1、列表项布局代码：
  <?xml version="1.0" encoding="utf-8"?>
	<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
		android:orientation="horizontal"
		android:layout_width="match_parent"
		android:layout_height="match_parent">

		<ImageView
			android:id="@+id/fruit_image"
			android:layout_width="wrap_content"
			android:layout_height="wrap_content" />

		<TextView
			android:id="@+id/fruit_name"
			android:layout_width="wrap_content"
			android:layout_height="wrap_content"
			android:layout_gravity="center_vertical"
			android:layout_marginLeft="10dp" />

	</LinearLayout>
2、自定义Adapter继承自ArrayAdapter<Fruit>
	public class FruitAdapter extends ArrayAdapter<Fruit> {

		private int resourceId;
		public FruitAdapter(Context context, int textViewResourceId,
							List<Fruit> objects) {
			super(context, textViewResourceId, objects);
			resourceId = textViewResourceId;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Fruit fruit = getItem(position); // 获取当前项的Fruit实例
			View view;
			ViewHolder viewHolder;
			if (convertView == null) {
				view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
				viewHolder = new ViewHolder();
				viewHolder.fruitImage = (ImageView) view.findViewById (R.id.fruit_image);
				viewHolder.fruitName = (TextView) view.findViewById (R.id.fruit_name);
				view.setTag(viewHolder); // 将ViewHolder存储在View中
			} else {
				view = convertView;
				viewHolder = (ViewHolder) view.getTag(); // 重新获取ViewHolder
			}
			viewHolder.fruitImage.setImageResource(fruit.getImageId());
			viewHolder.fruitName.setText(fruit.getName());
			return view;
		}

		class ViewHolder {
			ImageView fruitImage;
			TextView fruitName;
		}
	}

	//Fruit类的代码
	public class Fruit {

		private String name;

		private int imageId;

		public Fruit(String name, int imageId) {
			this.name = name;
			this.imageId = imageId;
		}

		public String getName() {
			return name;
		}

		public int getImageId() {
			return imageId;
		}
	}

3、MainActivity代码如下：
	public class MainActivity extends AppCompatActivity {

		private List<Fruit> fruitList = new ArrayList<Fruit>();

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_main);
			initFruits(); // 初始化水果数据
			FruitAdapter adapter = new FruitAdapter(MainActivity.this, R.layout.fruit_item, fruitList);
			ListView listView = (ListView) findViewById(R.id.list_view);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
										int position, long id) {
					Fruit fruit = fruitList.get(position);
					Toast.makeText(MainActivity.this, fruit.getName(), Toast.LENGTH_SHORT).show();
				}
			});
		}

		private void initFruits() {
			for (int i = 0; i < 2; i++) {
				Fruit apple = new Fruit("Apple", R.drawable.apple_pic);
				fruitList.add(apple);
				Fruit banana = new Fruit("Banana", R.drawable.banana_pic);
				fruitList.add(banana);
				Fruit orange = new Fruit("Orange", R.drawable.orange_pic);
				fruitList.add(orange);
				Fruit watermelon = new Fruit("Watermelon", R.drawable.watermelon_pic);
				fruitList.add(watermelon);
				Fruit pear = new Fruit("Pear", R.drawable.pear_pic);
				fruitList.add(pear);
				Fruit grape = new Fruit("Grape", R.drawable.grape_pic);
				fruitList.add(grape);
				Fruit pineapple = new Fruit("Pineapple", R.drawable.pineapple_pic);
				fruitList.add(pineapple);
				Fruit strawberry = new Fruit("Strawberry", R.drawable.strawberry_pic);
				fruitList.add(strawberry);
				Fruit cherry = new Fruit("Cherry", R.drawable.cherry_pic);
				fruitList.add(cherry);
				Fruit mango = new Fruit("Mango", R.drawable.mango_pic);
				fruitList.add(mango);
			}
		}
	}
