import UIKit

class MonthYearPickerController: UIViewController, UIPickerViewDataSource, UIPickerViewDelegate {

    // MARK: - Public closures
    var onDateSelected: ((_ date: Date) -> Void)?
    var onCanceled: (() -> Void)?
    var onBackdropDismissed: (() -> Void)?

    // MARK: - Public variables
    var selectedDate = Date()
    var maxDate: Date?
    var minDate: Date?
    var titleText: String?
    var cancelText: String?
    var doneText: String = "Done"
    var locale: Locale?
    var theme: Theme = .auto

    // MARK: - Private variables
    private let barViewHeight: CGFloat = 44
    private let pickerHeight: CGFloat = 216
    private let buttonWidth: CGFloat = 84
    private let lineHeight: CGFloat = 0.5
    private let buttonColor = UIColor(red: 72/255, green: 152/255, blue: 240/255, alpha: 1)
    private let lineColor = UIColor(red: 240/255, green: 240/255, blue: 240/255, alpha: 1)

    private let monthComponent = 0
    private let yearComponent = 1

    private var selectedMonth: Int = 0
    private var selectedYear: Int = 0
    private var minYear: Int = 0
    private var maxYear: Int = 0
    private var minMonth: Int = 0
    private var maxMonth: Int = 11
    private var monthNames: [String] = []

    // MARK: - Init
    init(title: String? = nil,
         cancelText: String? = nil,
         doneText: String = "Done",
         selectedDate: Date = Date(),
         minDate: Date? = nil,
         maxDate: Date? = nil,
         locale: Locale? = nil,
         theme: Theme = .auto) {

        self.titleText = title
        self.cancelText = cancelText
        self.doneText = doneText
        self.selectedDate = selectedDate
        self.minDate = minDate
        self.maxDate = maxDate
        self.locale = locale
        self.theme = theme

        super.init(nibName: nil, bundle: nil)

        configureDateRange()
        initialSetup()
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    override func traitCollectionDidChange(_ previousTraitCollection: UITraitCollection?) {
        // Trait collection has already changed
    }

    override func willTransition(to newCollection: UITraitCollection, with coordinator: UIViewControllerTransitionCoordinator) {
        if #available(iOS 12.0, *) {
            if newCollection.userInterfaceStyle != traitCollection.userInterfaceStyle {
                if newCollection.userInterfaceStyle == .dark {
                    setUpThemeMode(theme: theme, systemIsDark: true)
                } else {
                    setUpThemeMode(theme: theme, systemIsDark: false)
                }
            }
        } else {
            setUpThemeMode(theme: .light, systemIsDark: false)
        }
    }

    // MARK: - Private functions
    private func configureDateRange() {
        let calendar = Calendar.current
        let dateFormatter = DateFormatter()
        dateFormatter.locale = locale ?? Locale.current
        monthNames = dateFormatter.monthSymbols

        let currentComponents = calendar.dateComponents([.year, .month], from: selectedDate)
        selectedMonth = (currentComponents.month ?? 1) - 1
        selectedYear = currentComponents.year ?? calendar.component(.year, from: Date())

        if let minDate = minDate {
            let minComponents = calendar.dateComponents([.year, .month], from: minDate)
            minYear = minComponents.year ?? selectedYear - 100
            minMonth = (minComponents.month ?? 1) - 1
        } else {
            minYear = selectedYear - 100
            minMonth = 0
        }

        if let maxDate = maxDate {
            let maxComponents = calendar.dateComponents([.year, .month], from: maxDate)
            maxYear = maxComponents.year ?? selectedYear + 100
            maxMonth = (maxComponents.month ?? 12) - 1
        } else {
            maxYear = selectedYear + 100
            maxMonth = 11
        }
    }

    private func initialSetup() {

        view.backgroundColor = UIColor.clear
        let bgView = transView
        view.addSubview(bgView)
        bgView.fillConstraints(bgView: view)

        // Stack View
        stackView.addArrangedSubview(lineLabel)
        stackView.addArrangedSubview(toolBarView)
        stackView.addArrangedSubview(lineLabel)
        stackView.addArrangedSubview(monthYearPicker)

        let height = barViewHeight + (2*lineHeight) + pickerHeight

        self.view.addSubview(stackView)

        stackView.edgeConstraints( view, left: 0, right: 0, bottom: 0, height: height)

        monthYearPicker.selectRow(selectedMonth - monthRangeForYear(selectedYear).lowerBound, inComponent: monthComponent, animated: false)
        monthYearPicker.selectRow(selectedYear - minYear, inComponent: yearComponent, animated: false)

        if #available(iOS 12.0, *) {
            if traitCollection.userInterfaceStyle == .dark {
                setUpThemeMode(theme: theme, systemIsDark: true)
            } else {
                setUpThemeMode(theme: theme, systemIsDark: false)
            }
        } else {
            setUpThemeMode(theme: .light, systemIsDark: false)
        }
    }

    private func monthRangeForYear(_ year: Int) -> Range<Int> {
        let lower = (year == minYear) ? minMonth : 0
        let upper = (year == maxYear) ? maxMonth : 11
        return lower..<(upper + 1)
    }

    private func setUpThemeMode(theme: Theme, systemIsDark: Bool) {
        switch theme {
        case .light:
            applyLightTheme()
        case .dark:
            applyDarkTheme()
        case .auto:
            if systemIsDark {
                applyDarkTheme()
            } else {
                applyLightTheme()
            }
        }
    }

    private func applyLightTheme() {
        titleLabel.textColor = UIColor.darkGray
        stackView.backgroundColor = UIColor.white
        transView.backgroundColor = UIColor(white: 0.1, alpha: 0.3)
        monthYearPicker.backgroundColor = UIColor.white
        toolBarView.backgroundColor = UIColor.white
    }

    private func applyDarkTheme() {
        titleLabel.textColor = UIColor.white
        stackView.backgroundColor = UIColor.black
        transView.backgroundColor = UIColor(white: 1, alpha: 0.3)
        monthYearPicker.backgroundColor = UIColor.black
        toolBarView.backgroundColor = UIColor.black
    }

    private func dismissVC() {
        dismiss(animated: true, completion: nil)
    }

    @objc func handleTap() {
        onBackdropDismissed?()
        dismissVC()
    }

    // MARK: - UIPickerViewDataSource

    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 2
    }

    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        if component == monthComponent {
            return monthRangeForYear(selectedYear).count
        } else {
            return maxYear - minYear + 1
        }
    }

    // MARK: - UIPickerViewDelegate

    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        if component == yearComponent {
            let newYear = minYear + row
            let previousRange = monthRangeForYear(selectedYear)
            let previousMonthIndex = previousRange.lowerBound + monthYearPicker.selectedRow(inComponent: monthComponent)
            selectedYear = newYear

            let newRange = monthRangeForYear(newYear)
            monthYearPicker.reloadComponent(monthComponent)

            let clampedMonth = min(max(previousMonthIndex, newRange.lowerBound), newRange.upperBound - 1)
            selectedMonth = clampedMonth
            monthYearPicker.selectRow(clampedMonth - newRange.lowerBound, inComponent: monthComponent, animated: true)
        } else {
            let range = monthRangeForYear(selectedYear)
            selectedMonth = range.lowerBound + row
        }
    }

    func pickerView(_ pickerView: UIPickerView, attributedTitleForRow row: Int, forComponent component: Int) -> NSAttributedString? {
        let title: String
        if component == monthComponent {
            let monthIndex = monthRangeForYear(selectedYear).lowerBound + row
            title = monthNames[monthIndex]
        } else {
            title = String(minYear + row)
        }

        let isDark: Bool
        if #available(iOS 12.0, *) {
            switch theme {
            case .dark:
                isDark = true
            case .light:
                isDark = false
            case .auto:
                isDark = traitCollection.userInterfaceStyle == .dark
            }
        } else {
            isDark = false
        }

        let color = isDark ? UIColor.white : UIColor.black
        return NSAttributedString(string: title, attributes: [.foregroundColor: color])
    }

    // MARK: - Private properties

    private lazy var transView: UIView = {
        let vw = UIView()
        let tapGesture = UITapGestureRecognizer(target: self, action: #selector(self.handleTap))
        vw.addGestureRecognizer(tapGesture)
        vw.isUserInteractionEnabled = true
        return vw
    }()

    private lazy var stackView: UIStackView = {
        let sv = UIStackView()
        sv.axis = NSLayoutConstraint.Axis.vertical
        sv.distribution = UIStackView.Distribution.fill
        sv.alignment = UIStackView.Alignment.center
        sv.spacing = 0.0
        return sv
    }()

    private lazy var monthYearPicker: UIPickerView = {
        let picker = UIPickerView()
        picker.edgeConstraints( view, width: view.frame.width)
        picker.dataSource = self
        picker.delegate = self
        return picker
    }()

    private lazy var toolBarView: UIView = {

        let barView = UIView()
        barView.edgeConstraints( view, height: barViewHeight, width: view.frame.width)

        let doneButton = self.doneButton
        let cancelButton = self.cancelButton

        barView.addSubview(doneButton)
        barView.addSubview(cancelButton)

        cancelButton.edgeConstraints( barView, left: 0, top: 0, bottom: 0, width: buttonWidth)
        doneButton.edgeConstraints( barView, right: 0, top: 0, bottom: 0, width: buttonWidth)

        if let text = titleText {
            let titleLabel = self.titleLabel
            titleLabel.text = text
            barView.addSubview(titleLabel)
            titleLabel.fillConstraints(bgView: barView, left: buttonWidth, right: -buttonWidth)
        }

        doneButton.setTitle(doneText, for: .normal)

        if let text = cancelText {
            cancelButton.setTitle(text, for: .normal)
        } else {
            cancelButton.isHidden = true
        }

        return barView
    }()

    private lazy var lineLabel: UILabel = {
        let label = UILabel()
        label.backgroundColor = lineColor
        label.edgeConstraints( view, height: lineHeight, width: view.frame.width)
        return label
    }()

    private lazy var titleLabel: UILabel = {
        let label = UILabel()
        label.textAlignment = .center
        label.font = UIFont(name: "HelveticaNeue-Medium", size: 14)
        label.numberOfLines = 2
        return label
    }()

    private lazy var doneButton: UIButton = {
        let button = UIButton()
        button.setTitleColor(buttonColor, for: .normal)
        button.titleLabel?.font = UIFont(name: "HelveticaNeue-Bold", size: 16)
        button.addTarget(self, action: #selector(onDoneButton), for: .touchUpInside)
        return button
    }()

    private lazy var cancelButton: UIButton = {
        let button = UIButton()
        button.setTitleColor(buttonColor, for: .normal)
        button.titleLabel?.font = UIFont(name: "HelveticaNeue-Bold", size: 16)
        button.addTarget(self, action: #selector(onCancelButton), for: .touchUpInside)
        return button
    }()

    @objc func onDoneButton(sender: UIButton) {
        var components = DateComponents()
        components.year = selectedYear
        components.month = selectedMonth + 1
        components.day = 1
        if let date = Calendar.current.date(from: components) {
            onDateSelected?(date)
        }
        dismissVC()
    }

    @objc func onCancelButton(sender: UIButton) {
        onCanceled?()
        dismissVC()
    }
}

// MARK: - Private UIView constraint helpers

private extension UIView {

    func edgeConstraints(_ bgView: UIView, left: CGFloat? = nil, right: CGFloat? = nil, top: CGFloat? = nil, bottom: CGFloat? = nil, height: CGFloat? = nil, width: CGFloat? = nil) {

        translatesAutoresizingMaskIntoConstraints = false

        if let left = left { leftAnchor.constraint(equalTo: bgView.leftAnchor, constant: left).isActive = true }
        if let right = right { rightAnchor.constraint(equalTo: bgView.rightAnchor, constant: right).isActive = true }
        if let top = top { topAnchor.constraint(equalTo: bgView.topAnchor, constant: top).isActive = true }
        if let bottom = bottom { bottomAnchor.constraint(equalTo: bgView.bottomAnchor, constant: bottom).isActive = true }
        if let height = height { heightAnchor.constraint(equalToConstant: height).isActive = true }
        if let width = width { widthAnchor.constraint(equalToConstant: width).isActive = true }
    }

    func fillConstraints(bgView: UIView, left: CGFloat = 0, right: CGFloat = 0, top: CGFloat = 0, bottom: CGFloat = 0) {
        edgeConstraints(bgView, left: left, right: right, top: top, bottom: bottom)
    }
}
