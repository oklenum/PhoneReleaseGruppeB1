package com.groupb1.phonefreedom.presetList

/*
const val PRESET_ID = "preset id"

class PresetsListActivity : AppCompatActivity() {
    private val newPresetActivityRequestCode = 1
    private val presetsListViewModel by viewModels<PresetsListViewModel> {
        PresetsListViewModelFactory(this)
    }

    private val getResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
                val presetName = it.data?.getStringExtra(PRESET_NAME)
                val presetDescription = it.data?.getStringExtra(PRESET_DESCRIPTION)
                presetsListViewModel.insertPreset(presetName, presetDescription)
            }
        }


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_list_preset)

        val presetsAdapter = PresetsAdapter { preset -> adapterOnClick(preset)  }
        val recyclerView: RecyclerView = findViewById(R.id.presetList)
        presetsListViewModel.presetsLiveData.observe(this, {
            it?.let {
                presetsAdapter.submitList(it as MutableList<Preset>)

            }
        })

        val actionButton: View = findViewById(R.id.floatingActionButton)
        actionButton.setOnClickListener {
            actionButtonOnClick()
        }
    }

    private fun adapterOnClick(preset: Preset) {
        val intent = Intent(this, PresetDetailActivity()::class.java)
        intent.putExtra(PRESET_ID, preset.id)
        startActivity(intent)
    }

    private fun actionButtonOnClick() {
        val intent = Intent(this, AddPresetActivity::class.java)
        getResult.launch(intent)
    }

}

 */