1、打开 build.gradle 文件，在 dependencies 闭包中添加如下内容：
    compile 'com.android.support:recyclerview-v7:25.1.0'
2、列表项布局代码
    <?xml version="1.0" encoding="utf-8"?>
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
     android:orientation="vertical"
     android:layout_width="match_parent"
     android:layout_height="wrap_content"
     android:layout_margin="5dp" >
     <ImageView
         android:id="@+id/fruit_image"
         android:layout_width="wrap_content"
         android:layout_height="wrap_content"
         android:layout_gravity="center_horizontal" />
     <TextView
         android:id="@+id/fruit_name"
         android:layout_width="wrap_content"
         android:layout_height="wrap_content"
         android:layout_gravity="left"
         android:layout_marginTop="10dp" />
    </LinearLayout>
3、自定义 Adapter 继承自 RecyclerView.Adapter<FruitAdapter.ViewHolder>
    public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder>{
        private List<Fruit> mFruitList;
        static class ViewHolder extends RecyclerView.ViewHolder {
            View fruitView;
            ImageView fruitImage;
            TextView fruitName;
            ViewHolder(View view) {
                super(view);
                fruitView = view;
                fruitImage = (ImageView) view.findViewById(R.id.fruit_image);
                fruitName = (TextView) view.findViewById(R.id.fruit_name);
            }
        }
        public FruitAdapter(List<Fruit> fruitList) {
            mFruitList = fruitList;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fruit_item, parent, false);
            final ViewHolder holder = new ViewHolder(view);
            holder.fruitView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    Fruit fruit = mFruitList.get(position);
                    Toast.makeText(v.getContext(), "you clicked view " + fruit.getName(), Toast.LENGTH_SHORT).show();
                }
            });
            holder.fruitImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    Fruit fruit = mFruitList.get(position);
                    Toast.makeText(v.getContext(), "you clicked image " + fruit.getName(), Toast.LENGTH_SHORT).show();
                }
            });
            return holder;
        }
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Fruit fruit = mFruitList.get(position);
            holder.fruitImage.setImageResource(fruit.getImageId());
            holder.fruitName.setText(fruit.getName());
        }
        @Override
        public int getItemCount() {
            return mFruitList.size();
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

4、MainActivity代码如下：
    public class MainActivity extends AppCompatActivity {
        private List<Fruit> fruitList = new ArrayList<Fruit>();
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            initFruits();
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            /**
             * 常见布局如下
             */
    //        LinearLayoutManager layoutManager=new LinearLayoutManager(this);//线性布局
    //        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);//水平方向
    //        GridLayoutManager layoutManager=new GridLayoutManager(this,4);//网格布局,4列
            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL);//瀑布流布局,水平方向3列
            recyclerView.setLayoutManager(layoutManager);
            FruitAdapter adapter = new FruitAdapter(fruitList);
            recyclerView.setAdapter(adapter);
        }
        private void initFruits() {
            for (int i = 0; i < 2; i++) {
                Fruit apple = new Fruit(getRandomLengthName("Apple"), R.drawable.apple_pic);
                fruitList.add(apple);
                Fruit banana = new Fruit(getRandomLengthName("Banana"), R.drawable.banana_pic);
                fruitList.add(banana);
                Fruit orange = new Fruit(getRandomLengthName("Orange"), R.drawable.orange_pic);
                fruitList.add(orange);
                Fruit watermelon = new Fruit(getRandomLengthName("Watermelon"), R.drawable.watermelon_pic);
                fruitList.add(watermelon);
                Fruit pear = new Fruit(getRandomLengthName("Pear"), R.drawable.pear_pic);
                fruitList.add(pear);
                Fruit grape = new Fruit(getRandomLengthName("Grape"), R.drawable.grape_pic);
                fruitList.add(grape);
                Fruit pineapple = new Fruit(getRandomLengthName("Pineapple"), R.drawable.pineapple_pic);
                fruitList.add(pineapple);
                Fruit strawberry = new Fruit(getRandomLengthName("Strawberry"), R.drawable.strawberry_pic);
                fruitList.add(strawberry);
                Fruit cherry = new Fruit(getRandomLengthName("Cherry"), R.drawable.cherry_pic);
                fruitList.add(cherry);
                Fruit mango = new Fruit(getRandomLengthName("Mango"), R.drawable.mango_pic);
                fruitList.add(mango);
            }
        }
        private String getRandomLengthName(String name) {
            Random random = new Random();
            int length = random.nextInt(20) + 1;
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                builder.append(name);
            }
            return builder.toString();
        }
    }
